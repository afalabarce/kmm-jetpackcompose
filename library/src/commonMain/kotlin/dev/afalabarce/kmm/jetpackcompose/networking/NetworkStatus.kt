package dev.afalabarce.kmm.jetpackcompose.networking

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class NetworkConnectionType {
    data object Unknown : NetworkConnectionType()
    data object ConnectionCellular : NetworkConnectionType()
    data object Connection3G : NetworkConnectionType()
    data object Connection4G : NetworkConnectionType()
    data object Connection5G : NetworkConnectionType()
    data object ConnectionWifi : NetworkConnectionType()
    data object ConnectionEthernet : NetworkConnectionType()
}

sealed class NetworkStatus {
    object Available : NetworkStatus() {
        var connectionType: NetworkConnectionType = NetworkConnectionType.Connection3G
    }

    object Unavailable : NetworkStatus()
}

inline fun <Result> Flow<NetworkStatus>.map(
    crossinline onUnavailable: suspend () -> Result,
    crossinline onAvailable: suspend () -> Result,
): Flow<Result> = map { status ->
    when (status) {
        NetworkStatus.Unavailable -> onUnavailable()
        NetworkStatus.Available -> onAvailable()
    }
}