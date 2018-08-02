package fr.arthurvimond.oscletonsdk

import android.arch.lifecycle.LifecycleOwner
import fr.arthurvimond.oscletonsdk.di.Injector
import fr.arthurvimond.oscletonsdk.di.oscletonSDKModule
import fr.arthurvimond.oscletonsdk.exceptions.OscletonSDKException
import fr.arthurvimond.oscletonsdk.internal.AppLifecycleObserver
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
    private var sdkInitialized = false

    // Rx
    private val compositeDisposable = CompositeDisposable()

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

        sdkInitialized = true
    }

    private fun observeLifecycleObserverProperties() {

        lifecycleObserver.rxOnAppCreate()
                .subscribe {
                    connect()
                }
                .addTo(compositeDisposable)

        lifecycleObserver.rxOnAppDestroy()
                .subscribe {
                    disconnect()
                    dispose()
                }
                .addTo(compositeDisposable)

    }

    /**
     * Connect the device and prepare to listen for incoming changes.
     *
     * If attachLifecycleOwner() has been specified in an Activity's onCreate(),
     * this method will be automatically called by the SDK.
     *
     * NB: Calling this method before initialize() will throw an [OscletonSDKException].
     *
     * @see attachLifecycleOwner
     * @since 0.1
     */
    fun connect() {
        checkInitialized()

    }

    /**
     * Disconnect the device and close the incoming changes listener.
     *
     * If attachLifecycleOwner() has been specified in an Activity's onCreate(),
     * this method will be automatically called by the SDK.
     *
     * NB: Calling this method before initialize() will throw an [OscletonSDKException].
     *
     * @see attachLifecycleOwner
     * @since 0.1
     */
    fun disconnect() {
        checkInitialized()

    }

    /**
     * Start listening for incoming changes.
     *
     * If attachLifecycleOwner() has been specified in an Activity's onCreate(),
     * this method will be automatically called by the SDK.
     *
     * NB: Calling this method before initialize() will throw an [OscletonSDKException].
     *
     * @see attachLifecycleOwner
     * @since 0.1
     */
    fun startListening() {
        checkInitialized()

    }

    /**
     * Stop listening for incoming changes.
     *
     * If attachLifecycleOwner() has been specified in an Activity's onCreate(),
     * this method will be automatically called by the SDK.
     *
     * NB: Calling this method before initialize() will throw an [OscletonSDKException].
     *
     * @see attachLifecycleOwner
     * @since 0.1
     */
    fun stopListening() {
        checkInitialized()

    }

    /**
     * Dispose the SDK observers.
     *
     * This method must be called when the app is about to terminate.
     *
     * If attachLifecycleOwner() has been specified in an Activity's onCreate(),
     * this method will be automatically called by the SDK.
     *
     * NB: Calling this method before initialize() will throw an [OscletonSDKException].
     *
     * @see attachLifecycleOwner
     * @since 0.1
     */
    fun dispose() {
        checkInitialized()
        compositeDisposable.clear()

    }

    // LifecycleObserver

    /**
     * Attach a LifecycleOwner to the SDK.
     *
     * This method is used to let the SDK being lifecycle-aware,
     * so it will automatically call the necessary methods no matter
     * what the device state is (background, paused, resumed etc.).
     *
     * If used, this method must be called in each Activity's onCreate().
     * If not used, you will have to manually call the methods like
     * connect(), disconnect(), startListening(), stopListening() in their proper place.
     *
     * NB: Calling this method before initialize() will throw an [OscletonSDKException].
     *
     * @since 0.1
     */
    fun attachLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        checkInitialized()
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
    }

    /**
     * Detach a LifecycleOwner from the SDK.
     *
     * This method is used to let the SDK being lifecycle-aware,
     * so it will automatically call the necessary methods no matter
     * what the device state is (background, paused, resumed etc.).
     *
     * If used, this method, which goes with attachLifecycleOwner(),
     * must be called in each Activity's onDestroy(),
     * If not used, you will have to manually call the methods like
     * connect(), disconnect(), startListening(), stopListening() in their proper place.
     *
     * NB: Calling this method before initialize() will throw an [OscletonSDKException].
     *
     * @see attachLifecycleOwner
     * @since 0.1
     */
    fun detachLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        checkInitialized()
        lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
    }


    private fun checkInitialized() {
        if (!sdkInitialized) {
            throw OscletonSDKException("SDK not initialized")
        }
    }

}