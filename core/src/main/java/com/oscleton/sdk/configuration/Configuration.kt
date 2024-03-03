package com.oscleton.sdk.configuration

import com.oscleton.sdk.enums.AppTrack
import com.oscleton.sdk.enums.SDKResult
import com.oscleton.sdk.internal.CommonDataManager
import com.oscleton.sdk.internal.MessageManager
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
class Configuration internal constructor(
    private val configurationDataManager: ConfigurationDataManager,
    private val commonDataManager: CommonDataManager,
    private val messageManager: MessageManager
) {

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
     * @since 0.1
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
     * Disable a list of [LiveParameter][com.oscleton.sdk.enums.LiveParameter] so they won't be emitted.
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
     * Enable all [LiveParameter][com.oscleton.sdk.enums.LiveParameter]s so they will be emitted.
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