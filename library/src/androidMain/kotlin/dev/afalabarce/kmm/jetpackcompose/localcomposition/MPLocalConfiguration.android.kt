package dev.afalabarce.kmm.jetpackcompose.localcomposition

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import dev.afalabarce.kmm.jetpackcompose.networking.LocalNetworkStatus
import dev.afalabarce.kmm.jetpackcompose.networking.NetworkStatus
import dev.afalabarce.kmm.jetpackcompose.networking.NetworkStatusTracker

@Composable
actual fun setUiContent(content: @Composable () -> Unit) {
    val configuration = LocalConfiguration.current
    val currentNetworkStatus by NetworkStatusTracker(LocalContext.current)
        .networkStatus.collectAsState(initial = NetworkStatus.Available)

    CompositionLocalProvider(
        LocalNetworkStatus provides currentNetworkStatus,
        LocalKmpConfiguration provides MPConfiguration(
            screenOrientation = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) ScreenOrientation.PORTRAIT else ScreenOrientation.LANDSCAPE,
        )
    ) {
        content()
    }
}