package fr.arthurvimond.oscletonsdk

import android.arch.lifecycle.ProcessLifecycleOwner
import fr.arthurvimond.oscletonsdk.di.Injector
import fr.arthurvimond.oscletonsdk.di.oscletonSDKModule
import fr.arthurvimond.oscletonsdk.exceptions.OscletonSDKException
import fr.arthurvimond.oscletonsdk.internal.AppLifecycleObserver
import fr.arthurvimond.oscletonsdk.internal.MessageManager
import fr.arthurvimond.oscletonsdk.utils.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.inject

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

    val receiver: Receiver by injector.inject()

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
        loadKoinModules(listOf(oscletonSDKModule))

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
                    startListening()
                }
                .addTo(lifecycleCompositeDisposable)

        lifecycleObserver.rxOnAppBackground()
                .subscribe {
                    Logger.i("APP IS BACKGROUND", this)
                    disconnect()
                    stopListening()
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

    }

}