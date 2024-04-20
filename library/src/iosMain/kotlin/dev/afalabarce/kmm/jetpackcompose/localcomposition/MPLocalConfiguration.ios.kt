package dev.afalabarce.kmm.jetpackcompose.localcomposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.afalabarce.kmm.jetpackcompose.networking.LocalNetworkStatus
import dev.afalabarce.kmm.jetpackcompose.networking.NetworkStatus
import dev.afalabarce.kmm.jetpackcompose.networking.NetworkStatusTracker
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation

@Composable
actual fun setUiContent(content: @Composable () -> Unit) {
    val orientation: UIDeviceOrientation = UIDevice.currentDevice.orientation
    val currentNetworkStatus by NetworkStatusTracker()
        .networkStatus.collectAsState(initial = NetworkStatus.Available)
    CompositionLocalProvider(
        LocalNetworkStatus provides currentNetworkStatus,
        LocalKmpConfiguration provides MPConfiguration(
            screenOrientation = if (orientation.value.toInt() == 0) ScreenOrientation.PORTRAIT else ScreenOrientation.LANDSCAPE,
        )
    ) {
        content()
    }
}