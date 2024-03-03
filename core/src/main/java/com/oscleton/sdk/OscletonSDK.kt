package com.oscleton.sdk

import androidx.lifecycle.ProcessLifecycleOwner
import com.oscleton.sdk.browser.Browser
import com.oscleton.sdk.configuration.Configuration
import com.oscleton.sdk.devices.Devices
import com.oscleton.sdk.di.*
import com.oscleton.sdk.exceptions.OscletonSDKException
import com.oscleton.sdk.internal.AppLifecycleObserver
import com.oscleton.sdk.internal.MessageManager
import com.oscleton.sdk.liveset.LiveSet
import com.oscleton.sdk.tracks.Tracks
import com.oscleton.sdk.utils.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules

/**
 * The main entry point to use Oscleton SDK.
 *
 * This singleton provides multiple features split in different classes:
 * - [Configuration] is responsible for the settings needed to establish the connection.
 * - [LiveSet] lets you control the Live set from your Android device.
 * - [Tracks] lets you receive Tracks events in real time on your Android device.
 * - [Devices] lets you receive Devices events in real time on your Android device.
 * - [Browser] lets you control the Live Browser from your Android device.
 *
 * @since 0.1
 */
class OscletonSDK {

    companion object {
        @JvmStatic
        val instance by lazy { OscletonSDK() }
    }

    // Private properties

    private val injector by lazy { Injector() }
    private val lifecycleObserver: AppLifecycleObserver by injector.inject()
    private val messageManager: MessageManager by injector.inject()
    private var sdkInitialized = false

    // Rx
    private val compositeDisposable = CompositeDisposable()
    private val lifecycleCompositeDisposable = CompositeDisposable()

    // Public properties

    val config: Configuration by injector.inject()
    val liveSet: LiveSet by injector.inject()
    val tracks: Tracks by injector.inject()
    val devices: Devices by injector.inject()
    val browser: Browser by injector.inject()

    // Public methods

    /**
     * Initialize the SDK.
     *
     * This method must be called at application launch,
     * usually in the custom Application's onCreate() method.
     *
     * @since 0.1
     */
    fun initialize() {
        Logger.i("initialize", this)

        if (sdkInitialized) {
            return
        }

        loadKoinModules(listOf(
                oscletonSDKModule,
                liveSetModule,
                tracksModule,
                devicesModule,
                configurationModule,
                browserModule))

        observeLifecycleObserverProperties()
        attachApplicationLifecycle()

        sdkInitialized = true
    }

    private fun checkInitialized() {
        if (!sdkInitialized) {
            throw OscletonSDKException("SDK not initialized")
        }
    }

    private fun observeLifecycleObserverProperties() {

        lifecycleObserver.rxOnAppCreate()
                .subscribe {
                    Logger.i("APP IS CREATED", this)
                }
                .addTo(lifecycleCompositeDisposable)

        lifecycleObserver.rxOnAppForeground()
                .subscribe {
                    Logger.i("APP IS FOREGROUND", this)
                    connect()
                }
                .addTo(lifecycleCompositeDisposable)

        lifecycleObserver.rxOnAppBackground()
                .subscribe {
                    Logger.i("APP IS BACKGROUND", this)
                    disconnect()
                }
                .addTo(lifecycleCompositeDisposable)

    }

    private fun attachApplicationLifecycle() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
    }

    /**
     * Connect the device and prepare to listen for incoming changes.
     *
     * Since OscletonSDK is lifecycle-aware,
     * this method is automatically called when the app goes to foreground.
     *
     * NB: Calling this method before initialize() will throw an [OscletonSDKException].
     *
     * @since 0.1
     */
    fun connect() {
        checkInitialized()
        messageManager.connect()
    }

    /**
     * Disconnect the device and close the incoming changes listener.
     *
     * Since OscletonSDK is lifecycle-aware,
     * this method is automatically called when the app goes to background.
     *
     * NB: Calling this method before initialize() will throw an [OscletonSDKException].
     *
     * @since 0.1
     */
    fun disconnect() {
        checkInitialized()
        messageManager.disconnect()
    }

    /**
     * Start listening for incoming changes.
     *
     * Since OscletonSDK is lifecycle-aware,
     * this method is automatically called when the app goes to foreground.
     *
     * NB: Calling this method before initialize() will throw an [OscletonSDKException].
     *
     * @since 0.1
     */
    fun startListening() {
        checkInitialized()
        messageManager.startListening()
    }

    /**
     * Stop listening for incoming changes.
     *
     * Since OscletonSDK is lifecycle-aware,
     * this method is automatically called when the app goes to background.
     *
     * NB: Calling this method before initialize() will throw an [OscletonSDKException].
     *
     * @since 0.1
     */
    fun stopListening() {
        checkInitialized()
        messageManager.stopListening()
    }

}