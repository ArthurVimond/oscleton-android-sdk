package fr.arthurvimond.oscletonsdk

import fr.arthurvimond.oscletonsdk.enums.SDKResult
import fr.arthurvimond.oscletonsdk.internal.LiveSetDataManager
import fr.arthurvimond.oscletonsdk.internal.MessageManager
import fr.arthurvimond.oscletonsdk.utils.Empty
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

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

    @JvmSynthetic
    internal fun disposeObservers() {
        compositeDisposable.clear()
    }

}