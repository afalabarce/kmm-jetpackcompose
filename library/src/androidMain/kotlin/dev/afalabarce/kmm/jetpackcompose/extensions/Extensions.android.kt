package dev.afalabarce.kmm.jetpackcompose.extensions

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.scale

@Composable
actual fun systemDensity(): Float = LocalContext.current.resources.displayMetrics.density

@Composable
actual fun ImageBitmap.scale(width: Float, height: Float): ImageBitmap {
    val bitmap: Bitmap = this.asAndroidBitmap()
    return bitmap.scale(width.toInt(), height.toInt()).asImageBitmap()
}