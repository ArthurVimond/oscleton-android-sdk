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

/**
 * Extension function providing the ConfigurationRx class.
 * In Kotlin, you can directly call `rx()` to access [ConfigurationRx].
 * In Java, you have to call `CallbackProvider.from(Configuration)` to access [ConfigurationRx].
 *
 * @return ConfigurationRx
 * @since 1.0
 */
@JvmName("from")
fun Configuration.rx(): ConfigurationRx {
    loadKoinModulesIfNeeded()
    val configurationRx: ConfigurationRx by injector.inject()
    return configurationRx
}

/**
 * Extension function providing the LiveSet class.
 * In Kotlin, you can directly call `rx()` to access [LiveSetRx].
 * In Java, you have to call `CallbackProvider.from(LiveSet)` to access [LiveSetRx].
 *
 * @return LiveSet
 * @since 1.0
 */
@JvmName("from")
fun LiveSet.rx(): LiveSetRx {
    loadKoinModulesIfNeeded()
    val liveSetRx: LiveSetRx by injector.inject()
    return liveSetRx
}

/**
 * Extension function providing the TracksRx class.
 * In Kotlin, you can directly call `rx()` to access [TracksRx].
 * In Java, you have to call `CallbackProvider.from(Tracks)` to access [TracksRx].
 *
 * @return TracksRx
 * @since 1.0
 */
@JvmName("from")
fun Tracks.rx(): TracksRx {
    loadKoinModulesIfNeeded()
    val tracksRx: TracksRx by injector.inject()
    return tracksRx
}

/**
 * Extension function providing the DevicesRx class.
 * In Kotlin, you can directly call `rx()` to access [DevicesRx].
 * In Java, you have to call `CallbackProvider.from(Devices)` to access [DevicesRx].
 *
 * @return DevicesRx
 * @since 1.0
 */
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