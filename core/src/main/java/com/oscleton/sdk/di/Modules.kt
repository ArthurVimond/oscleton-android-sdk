package com.oscleton.sdk.di

import com.google.gson.GsonBuilder
import com.oscleton.sdk.*
import com.oscleton.sdk.browser.Browser
import com.oscleton.sdk.browser.BrowserDataManager
import com.oscleton.sdk.configuration.Configuration
import com.oscleton.sdk.configuration.ConfigurationDataManager
import com.oscleton.sdk.devices.Devices
import com.oscleton.sdk.devices.DevicesDataManager
import com.oscleton.sdk.internal.AppLifecycleObserver
import com.oscleton.sdk.internal.LiveSetDataManager
import com.oscleton.sdk.liveset.LiveSetDataManager
import com.oscleton.sdk.internal.MessageManager
import com.oscleton.sdk.internal.OSCManager
import com.oscleton.sdk.liveset.LiveSet
import com.oscleton.sdk.tracks.Tracks
import com.oscleton.sdk.tracks.TracksDataManager
import org.koin.dsl.module

internal val oscletonSDKModule = module {
    single { Configuration(get(), get()) }
    single { Controller(get()) }
    single { Receiver() }
    single { ReactiveReceiver(get()) }
    single { CallbackReceiver(get()) }
    single { MessageManager(get(), get()) }
    single { OSCManager() }
    single { AppLifecycleObserver() }
    single {
        val builder = GsonBuilder()
        return@single builder.create()
    }
}

internal val liveSetModule = module {
    single { LiveSet(get(), get()) }
    single { LiveSetDataManager(get(), get()) }
}

internal val tracksModule = module {
    single { Tracks(get(), get()) }
    single { TracksDataManager(get(), get()) }
}

internal val devicesModule = module {
    single { Devices(get(), get()) }
    single { DevicesDataManager(get(), get()) }
}

internal val configurationModule = module {
    single { Configuration(get(), get(), get()) }
    single { ConfigurationDataManager(get()) }
}

internal val browserModule = module {
    single { Browser(get(), get()) }
    single { BrowserDataManager(get(), get()) }
}