package dev.afalabarce.kmm.jetpackcompose.extensions

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Rect
import org.jetbrains.skia.Surface
import platform.UIKit.UIScreen

actual fun systemDensity(): Float = UIScreen.mainScreen.scale.toFloat()


actual fun ImageBitmap.scale(width: Float, height: Float): ImageBitmap {
    val surface = Surface.makeRasterN32Premul(width.toInt(), height.toInt())

    surface.canvas.drawImageRect(
        Image.makeFromBitmap(this.asSkiaBitmap()),
        Rect(0f, 0f, width, height),
        Paint().apply { isAntiAlias = true }
    )

    return surface.makeImageSnapshot().toComposeImageBitmap()
}