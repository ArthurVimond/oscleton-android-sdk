package fr.arthurvimond.oscletonsdk

import fr.arthurvimond.oscletonsdk.internal.MessageManager
import io.reactivex.disposables.CompositeDisposable

/**
 * Configuration is responsible for the SDK settings,
 * like the IP address in order to establish the connection between
 * the Android device and the computer running Live.
 *
 * @since 0.1
 */
class Configuration internal constructor(private val messageManager: MessageManager) {

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
     */
    fun setComputerIP(ip: String) {
        messageManager.initSender(ip)
    }

    @JvmSynthetic
    internal fun disposeObservers() {
        compositeDisposable.clear()
    }

}