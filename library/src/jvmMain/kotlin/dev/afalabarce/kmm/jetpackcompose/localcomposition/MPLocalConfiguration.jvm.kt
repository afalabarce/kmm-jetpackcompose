package dev.afalabarce.kmm.jetpackcompose.localcomposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.afalabarce.kmm.jetpackcompose.networking.LocalNetworkStatus
import dev.afalabarce.kmm.jetpackcompose.networking.NetworkStatus
import dev.afalabarce.kmm.jetpackcompose.networking.NetworkStatusTracker

@Composable
actual fun setUiContent(content: @Composable () -> Unit) {
    val currentNetworkStatus by NetworkStatusTracker()
        .networkStatus.collectAsState(initial = NetworkStatus.Available)

    CompositionLocalProvider(
        LocalNetworkStatus provides currentNetworkStatus,
        LocalKmpConfiguration provides MPConfiguration(
            screenOrientation = ScreenOrientation.DESKTOP
        )
    ) {
        content()
    }
}