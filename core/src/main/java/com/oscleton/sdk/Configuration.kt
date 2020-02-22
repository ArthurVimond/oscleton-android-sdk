package com.oscleton.sdk

import com.oscleton.sdk.enums.SDKResult
import com.oscleton.sdk.internal.LiveSetDataManager
import com.oscleton.sdk.internal.MessageManager
import com.oscleton.sdk.listeners.OnConnectionSuccessListener
import com.oscleton.sdk.listeners.OnQuitListener
import com.oscleton.sdk.listeners.OnStartListener
import com.oscleton.sdk.utils.Empty
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

/**
 * Configuration is responsible for the SDK settings,
 * like the IP address in order to establish the connection between
 * the Android device and the computer running Live.
 *
 * @since 0.1
 */
class Configuration internal constructor(private val liveSetDataManager: LiveSetDataManager,
                                         private val messageManager: MessageManager) {

    /**
     * Message sent when Live starts.
     *
     * @return Start message
     * @since 0.6
     */
    val onStart: Observable<Empty> = liveSetDataManager.onStart

    /**
     * Message sent when Live quits.
     *
     * @return Quit message
     * @since 0.6
     */
    val onQuit: Observable<Empty> = liveSetDataManager.onQuit

    /**
     * Oscleton SDK version
     *
     * @return the Oscleton SDK version
     * @since 0.1
     */
    val sdkVersion: String by lazy { BuildConfig.VERSION_NAME }

    /**
     * Live software version
     *
     * @return the Live software version
     * @since 0.1
     */
    val liveVersion: Observable<String> = liveSetDataManager.liveVersion

    /**
     * Oscleton MIDI Remote Script version in Live
     *
     * @return Oscleton MIDI Remote Script version
     * @since 0.1
     */
    val scriptVersion: Observable<String> = liveSetDataManager.scriptVersion

    /**
     * Connection success message in response of [setComputerIP] call.
     *
     * @return Connection success message
     * @since 0.2
     * @see setComputerIP
     */
    val onConnectionSuccess: Observable<Empty> = liveSetDataManager.onSetPeerSuccess

    /**
     * Connection error message in response of [setComputerIP] call.
     *
     * @return Connection error message
     * @since 0.6
     * @see setComputerIP
     */
    val onConnectionError: Observable<String> = messageManager.onSetComputerIPError

    /**
     * Discovery start message in response of [startComputerIPDiscovery] call.
     *
     * @return Discovery start message
     * @since 0.6
     * @see startComputerIPDiscovery
     */
    val onComputerIPDiscoveryStart: Observable<Empty> = messageManager.onComputerIPDiscoveryStart

    /**
     * Discovery progress indicator in response of [startComputerIPDiscovery] call, from 0 to 1.
     *
     * @return Discovery progress message
     * @since 0.6
     * @see startComputerIPDiscovery
     */
    val onComputerIPDiscoveryProgress: Observable<Float> = messageManager.onComputerIPDiscoveryProgress

    /**
     * Discovery success message in response of [startComputerIPDiscovery] call.
     *
     * @return Discovery success message
     * @since 0.6
     * @see startComputerIPDiscovery
     */
    val onComputerIPDiscoverySuccess: Observable<String> = liveSetDataManager.onComputerIPDiscoverySuccess

    /**
     * Discovery error message in response of [startComputerIPDiscovery] call.
     *
     * @return Discovery error message
     * @since 0.6
     * @see startComputerIPDiscovery
     */
    val onComputerIPDiscoveryError: Observable<String> = messageManager.onComputerIPDiscoveryError

    // RxJava
    private val compositeDisposable = CompositeDisposable()

    private var onStartDisp: Disposable? = null
    private var onQuitDisp: Disposable? = null
    private var onConnectionSuccessDisp: Disposable? = null

    init {
        observeProperties()
    }

    private fun observeProperties() {

        // SetPeer success
        liveSetDataManager.onSetPeerSuccess
                .subscribe {
                    messageManager.requestCurrentState()
                }
                .addTo(compositeDisposable)

        // IP Discovery
        liveSetDataManager.onComputerIPDiscoverySuccess
                .subscribe {
                    messageManager.cancelIPDiscovery()
                    messageManager.requestCurrentState()
                }
                .addTo(compositeDisposable)

    }

    /**
     * Set the computer IP address in order to send actions.
     *
     * NB: The default port is 9000.
     *
     * @param ip the IP address of the computer running Ableton Live
     * @return the SDK result
     */
    fun setComputerIP(ip: String): SDKResult {
        return messageManager.setComputerIP(ip)
    }

    /**
     * Start the computer IP discovery in order to connect automatically
     * to the computer running Ableton Live.
     *
     * @since 0.6
     * @return the SDK result
     */
    fun startComputerIPDiscovery(): SDKResult {
        return messageManager.startIPDiscovery()
    }

    /**
     * Cancel the current computer IP discovery.
     *
     * @since 0.6
     */
    fun cancelComputerIPDiscovery() {
        return messageManager.cancelIPDiscovery()
    }

    /**
     * Register a callback to be invoked when Ableton Live starts
     *
     * @param listener The callback that will run
     * @since 0.6
     */
    fun set(listener: OnStartListener) {
        onStartDisp = liveSetDataManager.onStart
                .subscribe {
                    listener.onStart()
                }

        compositeDisposable.add(onStartDisp!!)
    }

    /**
     * Remove the current [OnStartListener].
     *
     * @since 0.6
     */
    fun removeOnStartListener() {
        onStartDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when Ableton Live quits
     *
     * @param listener The callback that will run
     * @since 0.6
     */
    fun set(listener: OnQuitListener) {
        onQuitDisp = liveSetDataManager.onQuit
                .subscribe {
                    listener.onQuit()
                }

        compositeDisposable.add(onQuitDisp!!)
    }

    /**
     * Remove the current [OnQuitListener].
     *
     * @since 0.6
     */
    fun removeOnQuitListener() {
        onQuitDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when the mobile device
     * is connected to the computer running Ableton Live.
     *
     * @param listener The callback that will run
     * @since 0.2
     */
    fun set(listener: OnConnectionSuccessListener) {
        onConnectionSuccessDisp = liveSetDataManager.onSetPeerSuccess
                .subscribe {
                    listener.onConnectionSuccess()
                }

        compositeDisposable.add(onConnectionSuccessDisp!!)
    }

    /**
     * Remove the current [OnConnectionSuccessListener].
     *
     * @since 0.2
     */
    fun removeOnConnectionSuccessListener() {
        onConnectionSuccessDisp?.dispose()
    }

    /**
     * Disable a list of [LiveParameter][com.oscleton.sdk.enums.LiveParameter] so they won't be emitted by
     * [ReactiveReceiver][com.oscleton.sdk.ReactiveReceiver] or [CallbackReceiver][com.oscleton.sdk.CallbackReceiver].
     * Useful in case of breaking changes due to Oscleton SDK / MIDI Remote Script version incompatibility
     * to avoid unstable SDK behaviors.
     *
     * Updating the Oscleton SDK or MIDI Remote Script to the corresponding version will provide a full compatibility.
     * Please refer to the [Oscleton API](https://oscleton.herokuapp.com) to find the compatible versions.
     *
     * @since 0.5
     * @see enableAllLiveParameters
     */
    fun disableLiveParameters(parameters: List<String>) {
        liveSetDataManager.disableParameters(parameters)
    }

    /**
     * Enable all [LiveParameter][com.oscleton.sdk.enums.LiveParameter]s so they will be emitted by
     * [ReactiveReceiver][com.oscleton.sdk.ReactiveReceiver] or [CallbackReceiver][com.oscleton.sdk.CallbackReceiver].
     * Useful after having been disabled in case of breaking changes due to
     * Oscleton SDK / MIDI Remote Script version incompatibility.
     *
     * All parameters are enabled by default.
     *
     * @since 0.5
     * @see disableLiveParameters
     */
    fun enableAllLiveParameters() {
        liveSetDataManager.enableAllParameters()
    }

    @JvmSynthetic
    internal fun disposeObservers() {
        compositeDisposable.clear()
    }

}