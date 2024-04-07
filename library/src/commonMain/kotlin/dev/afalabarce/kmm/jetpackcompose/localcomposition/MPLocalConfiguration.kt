package dev.afalabarce.kmm.jetpackcompose.localcomposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

enum class ScreenOrientation {
    PORTRAIT,
    LANDSCAPE,
    DESKTOP
}

data class MPConfiguration(
    val screenOrientation: ScreenOrientation,
)

val LocalKmpConfiguration = compositionLocalOf {
    MPConfiguration(
        screenOrientation = ScreenOrientation.PORTRAIT,
    )
}

@Composable
expect fun setUiContent(content: @Composable () -> Unit)