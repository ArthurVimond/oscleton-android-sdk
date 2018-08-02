package fr.arthurvimond.oscletonsdk

import fr.arthurvimond.oscletonsdk.exceptions.OscletonSDKException
import kotlin.jvm.JvmStatic

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


        sdkInitialized = true
    }


    private fun checkInitialized() {
        if (!sdkInitialized) {
            throw OscletonSDKException("SDK not initialized")
        }
    }

}