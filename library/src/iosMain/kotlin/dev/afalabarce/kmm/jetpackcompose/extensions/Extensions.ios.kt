package dev.afalabarce.kmm.jetpackcompose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import dev.afalabarce.kmm.jetpackcompose.utilities.MPDecimalFormatSymbols
import kotlinx.cinterop.ExperimentalForeignApi
import org.jetbrains.skia.Image
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Rect
import org.jetbrains.skia.Surface
import platform.Foundation.NSDecimal
import platform.Foundation.NSNumber
import platform.UIKit.UIScreen

@Composable
actual fun systemDensity(): Float = UIScreen.mainScreen.scale.toFloat()

@Composable
actual fun ImageBitmap.scale(width: Float, height: Float): ImageBitmap {
    val surface = Surface.makeRasterN32Premul(width.toInt(), height.toInt())

    surface.canvas.drawImageRect(
        Image.makeFromBitmap(this.asSkiaBitmap()),
        Rect(0f, 0f, width, height),
        Paint().apply { isAntiAlias = true }
    )

    return surface.makeImageSnapshot().toComposeImageBitmap()
}

@OptIn(ExperimentalForeignApi::class)
actual fun String.isValidDecimal(): Boolean = try {
    val commaReplaced = this.replace(Comma, Dot)
    val decimalSeparator = MPDecimalFormatSymbols().decimalSeparator
    val correctDecimal = commaReplaced.replace(decimalSeparator, Dot)
    val result = correctDecimal.toDouble()
    true
} catch (exception: NumberFormatException) {
    false
}

private const val Comma = ','
private const val Dot = '.'