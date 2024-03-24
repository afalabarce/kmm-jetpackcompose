package dev.afalabarce.kmm.jetpackcompose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.*
import dev.afalabarce.kmm.jetpackcompose.utilities.MPDecimalFormatSymbols
import java.awt.Image
import java.awt.image.BufferedImage
import java.math.BigDecimal

@Composable
actual fun systemDensity(): Float = 1f

@Composable
actual fun ImageBitmap.scale(width: Float, height: Float): ImageBitmap {
    val scaled: Image = this.toAwtImage().getScaledInstance(
        width.toInt(),
        height.toInt(),
        Image.SCALE_SMOOTH
    )

    val scaledImage = BufferedImage(width.toInt(), height.toInt(), BufferedImage.TYPE_INT_ARGB)

    val graphics = scaledImage.graphics
    graphics.drawImage(scaled, 0, 0, null)
    graphics.dispose()

    return scaledImage.toComposeImageBitmap()
}

actual fun String.isValidDecimal(): Boolean = try {
    val commaReplaced = this.replace(Comma, Dot)
    val decimalSeparator = MPDecimalFormatSymbols().decimalSeparator
    val correctDecimal = commaReplaced.replace(decimalSeparator, Dot)
    BigDecimal(correctDecimal)
    true
} catch (exception: NumberFormatException) {
    false
}

private const val Comma = ','
private const val Dot = '.'