package com.oscleton.sdk.di

import com.oscleton.sdk.*
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
    single { LiveSetDataManager(get()) }
    single { AppLifecycleObserver() }
}
