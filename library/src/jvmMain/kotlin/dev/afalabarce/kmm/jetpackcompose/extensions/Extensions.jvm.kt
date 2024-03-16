package dev.afalabarce.kmm.jetpackcompose.extensions

import androidx.compose.ui.graphics.*
import java.awt.Image
import java.awt.image.BufferedImage

actual fun systemDensity(): Float = 1f
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
