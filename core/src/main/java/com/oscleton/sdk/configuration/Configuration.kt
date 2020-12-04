package com.oscleton.sdk.configuration

import com.oscleton.sdk.BuildConfig
import com.oscleton.sdk.enums.AppTrack
import com.oscleton.sdk.enums.SDKResult
import com.oscleton.sdk.internal.CommonDataManager
import com.oscleton.sdk.internal.MessageManager
import com.oscleton.sdk.listeners.*
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
class Configuration internal constructor(private val configurationDataManager: ConfigurationDataManager,
                                         private val commonDataManager: CommonDataManager,
                                         private val messageManager: MessageManager) {

    /**
     * Message sent when Live starts.
     *
     * @return Start message
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.rx().onStart instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.rx().onStart"))
    val onStart: Observable<Empty> = configurationDataManager.onStart

    /**
     * Message sent when Live quits.
     *
     * @return Quit message
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.rx().onQuit instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.rx().onQuit"))
    val onQuit: Observable<Empty> = configurationDataManager.onQuit

    /**
     * Oscleton SDK version
     *
     * @return the Oscleton SDK version
     * @since 0.1
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.rx().sdkVersion instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.rx().sdkVersion"))
    val sdkVersion: String by lazy { BuildConfig.VERSION_NAME }

    /**
     * Live software version
     *
     * @return the Live software version
     * @since 0.1
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.rx().liveVersion instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.rx().liveVersion"))
    val liveVersion: Observable<String> = configurationDataManager.liveVersion

    /**
     * Oscleton MIDI Remote Script version in Live
     *
     * @return Oscleton MIDI Remote Script version
     * @since 0.1
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.rx().scriptVersion instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.rx().scriptVersion"))
    val scriptVersion: Observable<String> = configurationDataManager.scriptVersion

    /**
     * Connection success message in response of [setComputerIP] call.
     *
     * @return Connection success message
     * @since 0.2
     * @see setComputerIP
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.rx().onConnectionSuccess instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.rx().onConnectionSuccess"))
    val onConnectionSuccess: Observable<Empty> = configurationDataManager.onSetPeerSuccess

    /**
     * Connection error message in response of [setComputerIP] call.
     *
     * @return Connection error message
     * @since 0.6
     * @see setComputerIP
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.rx().onConnectionError instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.rx().onConnectionError"))
    val onConnectionError: Observable<String> = messageManager.onSetComputerIPError

    /**
     * Discovery start message in response of [startComputerIPDiscovery] call.
     *
     * @return Discovery start message
     * @since 0.6
     * @see startComputerIPDiscovery
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.rx().onComputerIPDiscoveryStart instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.rx().onComputerIPDiscoveryStart"))
    val onComputerIPDiscoveryStart: Observable<Empty> = messageManager.onComputerIPDiscoveryStart

    /**
     * Discovery progress indicator in response of [startComputerIPDiscovery] call, from 0 to 1.
     *
     * @return Discovery progress message
     * @since 0.6
     * @see startComputerIPDiscovery
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.rx().onComputerIPDiscoveryProgress instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.rx().onComputerIPDiscoveryProgress"))
    val onComputerIPDiscoveryProgress: Observable<Float> = messageManager.onComputerIPDiscoveryProgress

    /**
     * Discovery success message in response of [startComputerIPDiscovery] call.
     *
     * @return Discovery success message
     * @since 0.6
     * @see startComputerIPDiscovery
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.rx().onComputerIPDiscoverySuccess instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.rx().onComputerIPDiscoverySuccess"))
    val onComputerIPDiscoverySuccess: Observable<String> = configurationDataManager.onComputerIPDiscoverySuccess

    /**
     * Discovery error message in response of [startComputerIPDiscovery] call.
     *
     * @return Discovery error message
     * @since 0.6
     * @see startComputerIPDiscovery
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.rx().onComputerIPDiscoveryError instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.rx().onComputerIPDiscoveryError"))
    val onComputerIPDiscoveryError: Observable<String> = messageManager.onComputerIPDiscoveryError

    /**
     * Discovery cancel message in response of [cancelComputerIPDiscovery] call.
     *
     * @return Discovery cancel message
     * @since 0.7
     * @see cancelComputerIPDiscovery
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.rx().onComputerIPDiscoveryCancel instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.rx().onComputerIPDiscoveryCancel"))
    val onComputerIPDiscoveryCancel: Observable<Empty> = messageManager.onComputerIPDiscoveryCancel

    // RxJava
    private val compositeDisposable = CompositeDisposable()

    private var onStartDisp: Disposable? = null
    private var onQuitDisp: Disposable? = null
    private var onConnectionSuccessDisp: Disposable? = null
    private var onConnectionErrorDisp: Disposable? = null
    private var onComputerIPDiscoveryStartDisp: Disposable? = null
    private var onComputerIPDiscoveryProgressDisp: Disposable? = null
    private var onComputerIPDiscoverySuccessDisp: Disposable? = null
    private var onComputerIPDiscoveryErrorDisp: Disposable? = null
    private var onComputerIPDiscoveryCancelDisp: Disposable? = null

    init {
        observeProperties()
    }

    private fun observeProperties() {

        // SetPeer success
        configurationDataManager.onSetPeerSuccess
                .subscribe {
                    messageManager.requestCurrentState()
                }
                .addTo(compositeDisposable)

        // IP Discovery
        configurationDataManager.onComputerIPDiscoverySuccess
                .subscribe {
                    messageManager.resetIPDiscovery()
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
     * Set the application track used for the Oscleton MIDI Remote Script auto-update process.
     * This also sets the application platform as "android".
     *
     * @param appTrack the application track
     * @see AppTrack
     */
    fun setAppTrack(appTrack: String) {
        messageManager.setAppTrack(appTrack)
        messageManager.setAppPlatform("android")
    }

    /**
     * Register a callback to be invoked when Ableton Live starts
     *
     * @param listener The callback that will run
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().set(listener)"))
    fun set(listener: OnStartListener) {
        onStartDisp = configurationDataManager.onStart
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
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().removeOnStartListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().removeOnStartListener()"))
    fun removeOnStartListener() {
        onStartDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when Ableton Live quits
     *
     * @param listener The callback that will run
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().set(listener)"))
    fun set(listener: OnQuitListener) {
        onQuitDisp = configurationDataManager.onQuit
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
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().removeOnQuitListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().removeOnQuitListener()"))
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
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().set(listener)"))
    fun set(listener: OnConnectionSuccessListener) {
        onConnectionSuccessDisp = configurationDataManager.onSetPeerSuccess
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
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().removeOnConnectionSuccessListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().removeOnConnectionSuccessListener()"))
    fun removeOnConnectionSuccessListener() {
        onConnectionSuccessDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when the connection
     * to the computer running Ableton Live failed.
     *
     * @param listener The callback that will run
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().set(listener)"))
    fun set(listener: OnConnectionErrorListener) {
        onConnectionErrorDisp = messageManager.onSetComputerIPError
                .subscribe {
                    listener.onConnectionError(it)
                }

        compositeDisposable.add(onConnectionErrorDisp!!)
    }

    /**
     * Remove the current [OnConnectionErrorListener].
     *
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().removeOnConnectionErrorListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().removeOnConnectionErrorListener()"))
    fun removeOnConnectionErrorListener() {
        onConnectionErrorDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when the computer IP discovery starts
     * in order to connect automatically to the computer running Ableton Live.
     *
     * @param listener The callback that will run
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().set(listener)"))
    fun set(listener: OnComputerIPDiscoveryStartListener) {
        onComputerIPDiscoveryStartDisp = messageManager.onComputerIPDiscoveryStart
                .subscribe {
                    listener.onComputerIPDiscoveryStart()
                }

        compositeDisposable.add(onComputerIPDiscoveryStartDisp!!)
    }

    /**
     * Remove the current [OnComputerIPDiscoveryStartListener].
     *
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().removeOnComputerIPDiscoveryStartListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().removeOnComputerIPDiscoveryStartListener()"))
    fun removeOnComputerIPDiscoveryStartListener() {
        onComputerIPDiscoveryStartDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when the computer IP discovery progress updates,
     * from 0 to 1.
     *
     * @param listener The callback that will run
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().set(listener)"))
    fun set(listener: OnComputerIPDiscoveryProgressListener) {
        onComputerIPDiscoveryProgressDisp = messageManager.onComputerIPDiscoveryProgress
                .subscribe {
                    listener.onComputerIPDiscoveryProgress(it)
                }

        compositeDisposable.add(onComputerIPDiscoveryProgressDisp!!)
    }

    /**
     * Remove the current [OnComputerIPDiscoveryProgressListener].
     *
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().removeOnComputerIPDiscoveryProgressListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().removeOnComputerIPDiscoveryProgressListener()"))
    fun removeOnComputerIPDiscoveryProgressListener() {
        onComputerIPDiscoveryProgressDisp?.dispose()
    }


    /**
     * Register a callback to be invoked when the computer IP discovery succeeded
     * to connect automatically to the computer running Ableton Live.
     *
     * @param listener The callback that will run
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().set(listener)"))
    fun set(listener: OnComputerIPDiscoverySuccessListener) {
        onComputerIPDiscoverySuccessDisp = configurationDataManager.onComputerIPDiscoverySuccess
                .subscribe {
                    listener.onComputerIPDiscoverySuccess(it)
                }

        compositeDisposable.add(onComputerIPDiscoverySuccessDisp!!)
    }

    /**
     * Remove the current [OnComputerIPDiscoverySuccessListener].
     *
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().removeOnComputerIPDiscoverySuccessListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().removeOnComputerIPDiscoverySuccessListener()"))
    fun removeOnComputerIPDiscoverySuccessListener() {
        onComputerIPDiscoverySuccessDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when the computer IP discovery failed
     * to connect automatically to the computer running Ableton Live.
     *
     * @param listener The callback that will run
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().set(listener)"))
    fun set(listener: OnComputerIPDiscoveryErrorListener) {
        onComputerIPDiscoveryErrorDisp = messageManager.onComputerIPDiscoveryError
                .subscribe {
                    listener.onComputerIPDiscoveryError(it)
                }

        compositeDisposable.add(onComputerIPDiscoveryErrorDisp!!)
    }

    /**
     * Remove the current [OnComputerIPDiscoveryErrorListener].
     *
     * @since 0.6
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().removeOnComputerIPDiscoveryErrorListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().removeOnComputerIPDiscoveryErrorListener()"))
    fun removeOnComputerIPDiscoveryErrorListener() {
        onComputerIPDiscoveryErrorDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when the computer IP discovery is cancelled.
     *
     * @param listener The callback that will run
     * @since 0.7
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().set(listener)"))
    fun set(listener: OnComputerIPDiscoveryCancelListener) {
        onComputerIPDiscoveryCancelDisp = messageManager.onComputerIPDiscoveryCancel
                .subscribe {
                    listener.onComputerIPDiscoveryCancel()
                }

        compositeDisposable.add(onComputerIPDiscoveryErrorDisp!!)
    }

    /**
     * Remove the current [OnComputerIPDiscoveryCancelListener].
     *
     * @since 0.7
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.config.cb().removeOnComputerIPDiscoveryCancelListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.config.cb().removeOnComputerIPDiscoveryCancelListener()"))
    fun removeOnComputerIPDiscoveryCancelListener() {
        onComputerIPDiscoveryCancelDisp?.dispose()
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
        commonDataManager.disableParameters(parameters)
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
        commonDataManager.enableAllParameters()
    }

    @JvmSynthetic
    internal fun disposeObservers() {
        compositeDisposable.clear()
    }

}