package com.oscleton.sdk

import com.oscleton.sdk.enums.SDKResult
import com.oscleton.sdk.internal.LiveSetDataManager
import com.oscleton.sdk.internal.MessageManager
import com.oscleton.sdk.listeners.OnConnectionSuccessListener
import com.oscleton.sdk.utils.Empty
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

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

    // RxJava
    private val compositeDisposable = CompositeDisposable()

    private var onConnectionSuccessDisp: Disposable? = null

    init {
        observeProperties()
    }

    private fun observeProperties() {

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
        return messageManager.initSender(ip)
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