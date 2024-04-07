package dev.afalabarce.kmm.jetpackcompose.localcomposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation

@Composable
actual fun setUiContent(content: @Composable () -> Unit) {
    val orientation: UIDeviceOrientation = UIDevice.currentDevice.orientation

    CompositionLocalProvider(
        LocalKmpConfiguration provides MPConfiguration(
            screenOrientation = if (orientation.value.toInt() == 0) ScreenOrientation.PORTRAIT else ScreenOrientation.LANDSCAPE,
        )
    ) {
        content()
    }
}