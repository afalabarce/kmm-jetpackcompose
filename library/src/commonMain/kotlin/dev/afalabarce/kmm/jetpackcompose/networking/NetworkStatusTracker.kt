package dev.afalabarce.kmm.jetpackcompose.networking

import kotlinx.coroutines.flow.Flow

expect class NetworkStatusTracker {
    val networkStatus: Flow<NetworkStatus>
}