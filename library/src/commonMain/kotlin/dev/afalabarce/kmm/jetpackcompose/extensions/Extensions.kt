package dev.afalabarce.kmm.jetpackcompose.extensions

import androidx.compose.ui.graphics.ImageBitmap

expect fun systemDensity(): Float

expect fun ImageBitmap.scale(width: Float, height: Float): ImageBitmap