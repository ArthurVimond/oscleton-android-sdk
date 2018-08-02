package fr.arthurvimond.oscletonsdk

import fr.arthurvimond.oscletonsdk.di.oscletonSDKModule
import fr.arthurvimond.oscletonsdk.exceptions.OscletonSDKException
import fr.arthurvimond.oscletonsdk.utils.Logger
import org.koin.standalone.StandAloneContext.loadKoinModules

class OscletonSDK {

    companion object {
        @JvmStatic
        val instance by lazy { OscletonSDK() }
    }

    // Private properties

    private var sdkInitialized = false

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

        sdkInitialized = true
    }


    private fun checkInitialized() {
        if (!sdkInitialized) {
            throw OscletonSDKException("SDK not initialized")
        }
    }

}