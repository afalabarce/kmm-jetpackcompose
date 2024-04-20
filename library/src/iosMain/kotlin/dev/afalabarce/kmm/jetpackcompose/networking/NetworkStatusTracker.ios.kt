@file:OptIn(ExperimentalForeignApi::class)

package dev.afalabarce.kmm.jetpackcompose.networking

import cocoapods.Reachability.Reachability
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import platform.Foundation.NSLog
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

actual class NetworkStatusTracker {
    @OptIn(ExperimentalForeignApi::class)
    private var kmpNetworkReachability: Reachability? = null

    init {
        dispatch_async(dispatch_get_main_queue()) {
            this.kmpNetworkReachability = Reachability.reachabilityForInternetConnection()
            this.kmpNetworkReachability?.reachableBlock = { reachability ->
                reachability?.let { reachStatus ->
                    (networkStatus as MutableStateFlow<NetworkStatus>).value = if (reachStatus.isReachable())
                        NetworkStatus.Available
                    else
                        NetworkStatus.Unavailable
                }
            }
            this.kmpNetworkReachability?.startNotifier()

            dispatch_async(dispatch_get_main_queue()) {
                (networkStatus as MutableStateFlow<NetworkStatus>).value =
                    if (this.kmpNetworkReachability?.isReachable() == true)
                        NetworkStatus.Available
                    else
                        NetworkStatus.Unavailable

                NSLog("Initial reachability: ${this.kmpNetworkReachability?.isReachable()}")
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual val networkStatus: Flow<NetworkStatus>
        get() = MutableStateFlow<NetworkStatus>(NetworkStatus.Unavailable)

}