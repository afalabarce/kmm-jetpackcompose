package dev.afalabarce.kmm.jetpackcompose.networking

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import java.net.NetworkInterface
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

actual class NetworkStatusTracker {
    private val refreshTimeOutInSeconds: Long = 3L
    actual val networkStatus: Flow<NetworkStatus>
        get() = callbackFlow {
            withContext(Dispatchers.IO) {
                try {
                    while (true) {
                        val withNetwork = NetworkInterface.networkInterfaces().anyMatch { networkInterface ->
                            !networkInterface.isLoopback && !networkInterface.isVirtual && networkInterface.isUp
                        }

                        trySend(
                            if (withNetwork)
                                NetworkStatus.Available
                            else
                                NetworkStatus.Unavailable
                        )

                        delay(refreshTimeOutInSeconds.seconds)
                    }
                } catch (_: Exception) {
                    /* do nothing */
                }
            }
        }
}