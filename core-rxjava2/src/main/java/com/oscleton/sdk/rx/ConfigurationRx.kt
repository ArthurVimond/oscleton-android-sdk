package com.oscleton.sdk.rx

import com.oscleton.sdk.BuildConfig
import com.oscleton.sdk.CallbackReceiver
import com.oscleton.sdk.configuration.ConfigurationDataManager
import com.oscleton.sdk.utils.Empty
import io.reactivex.Observable

/**
 * ConfigurationRx contains RxJava Observables reacting to the Live configuration changes.
 *
 * If a reactive approach doesn't fit your needs, consider using `ConfigurationCallbacks` from the 'core-callbacks' artifact instead.
 *
 * @since 1.0
 */
class ConfigurationRx internal constructor(private val configurationDataManager: ConfigurationDataManager) {

    /**
     * Message sent when Live starts.
     *
     * @return Start message
     * @since 1.0
     */
    val onStart: Observable<Empty> = configurationDataManager.onStart

    /**
     * Message sent when Live quits.
     *
     * @return Quit message
     * @since 1.0
     */
    val onQuit: Observable<Empty> = configurationDataManager.onQuit

    /**
     * Oscleton SDK version
     *
     * @return the Oscleton SDK version
     * @since 1.0
     */
    val sdkVersion: Observable<String> by lazy { Observable.just(BuildConfig.VERSION_NAME) }

    /**
     * Live software version
     *
     * @return the Live software version
     * @since 1.0
     */
    val liveVersion: Observable<String> = configurationDataManager.liveVersion

    /**
     * Oscleton MIDI Remote Script version in Live
     *
     * @return Oscleton MIDI Remote Script version
     * @since 1.0
     */
    val scriptVersion: Observable<String> = configurationDataManager.scriptVersion

    /**
     * Connection success message in response of [setComputerIP] call.
     *
     * @return Connection success message
     * @since 1.0
     * @see setComputerIP
     */
    val onConnectionSuccess: Observable<Empty> = configurationDataManager.onSetPeerSuccess

    /**
     * Connection error message in response of [setComputerIP] call.
     *
     * @return Connection error message
     * @since 1.0
     * @see setComputerIP
     */
    val onConnectionError: Observable<String> = configurationDataManager.onSetComputerIPError

    /**
     * Discovery start message in response of [startComputerIPDiscovery] call.
     *
     * @return Discovery start message
     * @since 1.0
     * @see startComputerIPDiscovery
     */
    val onComputerIPDiscoveryStart: Observable<Empty> = configurationDataManager.onComputerIPDiscoveryStart

    /**
     * Discovery progress indicator in response of [startComputerIPDiscovery] call, from 0 to 1.
     *
     * @return Discovery progress message
     * @since 1.0
     * @see startComputerIPDiscovery
     */
    val onComputerIPDiscoveryProgress: Observable<Float> = configurationDataManager.onComputerIPDiscoveryProgress

    /**
     * Discovery success message in response of [startComputerIPDiscovery] call.
     *
     * @return Discovery success message
     * @since 1.0
     * @see startComputerIPDiscovery
     */
    val onComputerIPDiscoverySuccess: Observable<String> = configurationDataManager.onComputerIPDiscoverySuccess

    /**
     * Discovery error message in response of [startComputerIPDiscovery] call.
     *
     * @return Discovery error message
     * @since 1.0
     * @see startComputerIPDiscovery
     */
    val onComputerIPDiscoveryError: Observable<String> = configurationDataManager.onComputerIPDiscoveryError

    /**
     * Discovery cancel message in response of [cancelComputerIPDiscovery] call.
     *
     * @return Discovery cancel message
     * @since 1.0
     * @see cancelComputerIPDiscovery
     */
    val onComputerIPDiscoveryCancel: Observable<Empty> = configurationDataManager.onComputerIPDiscoveryCancel

}