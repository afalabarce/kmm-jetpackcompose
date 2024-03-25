package dev.afalabarce.kmm.jetpackcompose.localcomposition

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration

@Composable
actual fun setUiContent(content: @Composable () -> Unit) {
    val configuration = LocalConfiguration.current
    CompositionLocalProvider(
        LocalKmpConfiguration provides MPConfiguration(
            isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        )
    ) {
        content()
    }
}