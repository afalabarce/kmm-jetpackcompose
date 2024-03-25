package dev.afalabarce.kmm.jetpackcompose.localcomposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
actual fun setUiContent(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalKmpConfiguration provides MPConfiguration(
            isPortrait = false
        )
    ) {
        content()
    }
}