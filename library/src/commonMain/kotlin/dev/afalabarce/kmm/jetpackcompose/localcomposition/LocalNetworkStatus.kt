package dev.afalabarce.kmm.jetpackcompose.networking

import androidx.compose.runtime.compositionLocalOf

val LocalNetworkStatus = compositionLocalOf<NetworkStatus> {
    NetworkStatus.Available
}