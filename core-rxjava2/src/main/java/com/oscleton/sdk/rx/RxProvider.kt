@file:JvmName("RxProvider")

package com.oscleton.sdk.rx

import com.oscleton.sdk.configuration.Configuration
import com.oscleton.sdk.devices.Devices
import com.oscleton.sdk.di.Injector
import com.oscleton.sdk.liveset.LiveSet
import com.oscleton.sdk.rx.di.reactiveModule
import com.oscleton.sdk.tracks.Tracks
import org.koin.core.context.loadKoinModules
import org.koin.core.inject

private val injector by lazy { Injector() }
private var koinModulesLoaded = false

@JvmName("from")
fun Configuration.rx(): ConfigurationRx {
    loadKoinModulesIfNeeded()
    val configurationRx: ConfigurationRx by injector.inject()
    return configurationRx
}

@JvmName("from")
fun LiveSet.rx(): LiveSetRx {
    loadKoinModulesIfNeeded()
    val liveSetRx: LiveSetRx by injector.inject()
    return liveSetRx
}

@JvmName("from")
fun Tracks.rx(): TracksRx {
    loadKoinModulesIfNeeded()
    val tracksRx: TracksRx by injector.inject()
    return tracksRx
}

@JvmName("from")
fun Devices.rx(): DevicesRx {
    loadKoinModulesIfNeeded()
    val devicesRx: DevicesRx by injector.inject()
    return devicesRx
}

private fun loadKoinModulesIfNeeded() {
    if (!koinModulesLoaded) {
        loadKoinModules(listOf(reactiveModule))
        koinModulesLoaded = true
    }
}