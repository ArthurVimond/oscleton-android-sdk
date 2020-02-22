package com.oscleton.sdk.sample.base

import android.app.Application
import com.oscleton.sdk.OscletonSDK
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SampleApp)
        }

        // Initialize SDK
        OscletonSDK.instance.initialize()
    }
}