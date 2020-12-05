@file:JvmName("CallbackProvider")

package com.oscleton.sdk.callbacks

import com.oscleton.sdk.callbacks.configuration.ConfigurationCallbacks
import com.oscleton.sdk.callbacks.devices.DevicesCallbacks
import com.oscleton.sdk.callbacks.di.callbacksModule
import com.oscleton.sdk.callbacks.liveset.LiveSetCallbacks
import com.oscleton.sdk.callbacks.tracks.TracksCallbacks
import com.oscleton.sdk.configuration.Configuration
import com.oscleton.sdk.devices.Devices
import com.oscleton.sdk.di.Injector
import com.oscleton.sdk.liveset.LiveSet
import com.oscleton.sdk.tracks.Tracks
import org.koin.core.context.loadKoinModules
import org.koin.core.inject

private val injector by lazy { Injector() }
private var koinModulesLoaded = false

@JvmName("from")
fun Configuration.cb(): ConfigurationCallbacks {
    loadKoinModulesIfNeeded()
    val configurationCallbacks: ConfigurationCallbacks by injector.inject()
    return configurationCallbacks
}

@JvmName("from")
fun LiveSet.cb(): LiveSetCallbacks {
    loadKoinModulesIfNeeded()
    val liveSetCallbacks: LiveSetCallbacks by injector.inject()
    return liveSetCallbacks
}

@JvmName("from")
fun Tracks.cb(): TracksCallbacks {
    loadKoinModulesIfNeeded()
    val tracksCallbacks: TracksCallbacks by injector.inject()
    return tracksCallbacks
}

@JvmName("from")
fun Devices.cb(): DevicesCallbacks {
    loadKoinModulesIfNeeded()
    val devicesCallbacks: DevicesCallbacks by injector.inject()
    return devicesCallbacks
}

private fun loadKoinModulesIfNeeded() {
    if (!koinModulesLoaded) {
        loadKoinModules(listOf(callbacksModule))
        koinModulesLoaded = true
    }
}