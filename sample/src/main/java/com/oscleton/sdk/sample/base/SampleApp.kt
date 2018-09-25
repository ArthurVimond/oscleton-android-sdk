package com.oscleton.sdk.sample.base

import android.app.Application
import com.oscleton.sdk.OscletonSDK

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize SDK
        OscletonSDK.instance.initialize()
    }
}