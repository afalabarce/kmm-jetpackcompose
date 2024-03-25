package dev.afalabarce.kmm.jetpackcompose.localcomposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

data class MPConfiguration(val isPortrait: Boolean)

val LocalKmpConfiguration = compositionLocalOf { MPConfiguration(false) }

@Composable
expect fun setUiContent(content: @Composable () -> Unit)