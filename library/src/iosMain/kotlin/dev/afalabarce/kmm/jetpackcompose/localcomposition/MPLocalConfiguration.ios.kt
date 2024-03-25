package dev.afalabarce.kmm.jetpackcompose.localcomposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation
import platform.UIKit.UIDeviceOrientationIsLandscape

@Composable
actual fun setUiContent(content: @Composable () -> Unit) {
    val orientation: UIDeviceOrientation = UIDevice.currentDevice.orientation
    CompositionLocalProvider(
        LocalKmpConfiguration provides MPConfiguration(
            isPortrait = orientation.value.toInt() == 0
        )
    ) {
        content()
    }
}