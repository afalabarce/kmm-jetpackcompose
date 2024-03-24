package dev.afalabarce.kmm.jetpackcompose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
expect fun systemDensity(): Float

@Composable
expect fun ImageBitmap.scale(width: Float, height: Float): ImageBitmap

expect fun String.isValidDecimal(): Boolean