package fr.arthurvimond.oscletonsdk.sample.base

import android.app.Application
import fr.arthurvimond.oscletonsdk.OscletonSDK

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize SDK
        OscletonSDK.instance.initialize()
    }
}