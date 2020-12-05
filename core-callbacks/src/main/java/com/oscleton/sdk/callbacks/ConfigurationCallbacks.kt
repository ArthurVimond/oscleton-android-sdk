package com.oscleton.sdk.callbacks

import com.oscleton.sdk.callbacks.configuration.listeners.*
import com.oscleton.sdk.configuration.ConfigurationDataManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ConfigurationCallbacks internal constructor(private val configurationDataManager: ConfigurationDataManager) {

    // Rx
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

    /**
     * Register a callback to be invoked when Ableton Live starts
     *
     * @param listener The callback that will run
     * @since 1.0
     */
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
     * @since 1.0
     */
    fun removeOnStartListener() {
        onStartDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when Ableton Live quits
     *
     * @param listener The callback that will run
     * @since 1.0
     */
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
     * @since 1.0
     */
    fun removeOnQuitListener() {
        onQuitDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when the mobile device
     * is connected to the computer running Ableton Live.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
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
     * @since 1.0
     */
    fun removeOnConnectionSuccessListener() {
        onConnectionSuccessDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when the connection
     * to the computer running Ableton Live failed.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
    fun set(listener: OnConnectionErrorListener) {
        onConnectionErrorDisp = configurationDataManager.onSetComputerIPError
                .subscribe {
                    listener.onConnectionError(it)
                }

        compositeDisposable.add(onConnectionErrorDisp!!)
    }

    /**
     * Remove the current [OnConnectionErrorListener].
     *
     * @since 1.0
     */
    fun removeOnConnectionErrorListener() {
        onConnectionErrorDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when the computer IP discovery starts
     * in order to connect automatically to the computer running Ableton Live.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
    fun set(listener: OnComputerIPDiscoveryStartListener) {
        onComputerIPDiscoveryStartDisp = configurationDataManager.onComputerIPDiscoveryStart
                .subscribe {
                    listener.onComputerIPDiscoveryStart()
                }

        compositeDisposable.add(onComputerIPDiscoveryStartDisp!!)
    }

    /**
     * Remove the current [OnComputerIPDiscoveryStartListener].
     *
     * @since 1.0
     */
    fun removeOnComputerIPDiscoveryStartListener() {
        onComputerIPDiscoveryStartDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when the computer IP discovery progress updates,
     * from 0 to 1.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
    fun set(listener: OnComputerIPDiscoveryProgressListener) {
        onComputerIPDiscoveryProgressDisp = configurationDataManager.onComputerIPDiscoveryProgress
                .subscribe {
                    listener.onComputerIPDiscoveryProgress(it)
                }

        compositeDisposable.add(onComputerIPDiscoveryProgressDisp!!)
    }

    /**
     * Remove the current [OnComputerIPDiscoveryProgressListener].
     *
     * @since 1.0
     */
    fun removeOnComputerIPDiscoveryProgressListener() {
        onComputerIPDiscoveryProgressDisp?.dispose()
    }


    /**
     * Register a callback to be invoked when the computer IP discovery succeeded
     * to connect automatically to the computer running Ableton Live.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
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
     * @since 1.0
     */
    fun removeOnComputerIPDiscoverySuccessListener() {
        onComputerIPDiscoverySuccessDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when the computer IP discovery failed
     * to connect automatically to the computer running Ableton Live.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
    fun set(listener: OnComputerIPDiscoveryErrorListener) {
        onComputerIPDiscoveryErrorDisp = configurationDataManager.onComputerIPDiscoveryError
                .subscribe {
                    listener.onComputerIPDiscoveryError(it)
                }

        compositeDisposable.add(onComputerIPDiscoveryErrorDisp!!)
    }

    /**
     * Remove the current [OnComputerIPDiscoveryErrorListener].
     *
     * @since 1.0
     */
    fun removeOnComputerIPDiscoveryErrorListener() {
        onComputerIPDiscoveryErrorDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when the computer IP discovery is cancelled.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
    fun set(listener: OnComputerIPDiscoveryCancelListener) {
        onComputerIPDiscoveryCancelDisp = configurationDataManager.onComputerIPDiscoveryCancel
                .subscribe {
                    listener.onComputerIPDiscoveryCancel()
                }

        compositeDisposable.add(onComputerIPDiscoveryErrorDisp!!)
    }

    /**
     * Remove the current [OnComputerIPDiscoveryCancelListener].
     *
     * @since 1.0
     */
    fun removeOnComputerIPDiscoveryCancelListener() {
        onComputerIPDiscoveryCancelDisp?.dispose()
    }

    /**
     * Remove all listeners.
     *
     * @since 1.0
     */
    fun removeAllListeners() {
        compositeDisposable.clear()
    }

}