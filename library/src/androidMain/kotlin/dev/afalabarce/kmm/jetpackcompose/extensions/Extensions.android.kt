package dev.afalabarce.kmm.jetpackcompose.extensions

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.scale
import dev.afalabarce.kmm.jetpackcompose.utilities.MPDecimalFormatSymbols
import java.math.BigDecimal

@Composable
actual fun systemDensity(): Float = LocalContext.current.resources.displayMetrics.density

@Composable
actual fun ImageBitmap.scale(width: Float, height: Float): ImageBitmap {
    val bitmap: Bitmap = this.asAndroidBitmap()
    return bitmap.scale(width.toInt(), height.toInt()).asImageBitmap()
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