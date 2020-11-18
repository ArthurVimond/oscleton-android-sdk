package com.oscleton.sdk.di

import com.google.gson.GsonBuilder
import com.oscleton.sdk.*
import com.oscleton.sdk.browser.Browser
import com.oscleton.sdk.browser.BrowserDataManager
import com.oscleton.sdk.internal.AppLifecycleObserver
import com.oscleton.sdk.internal.LiveSetDataManager
import com.oscleton.sdk.internal.MessageManager
import com.oscleton.sdk.internal.OSCManager
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

    single { LiveSetDataManager(get()) }
}

internal val browserModule = module {
    single { Browser(get(), get()) }
    single { BrowserDataManager(get(), get()) }
}