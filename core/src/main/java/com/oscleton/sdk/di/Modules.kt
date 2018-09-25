package com.oscleton.sdk.di

import com.oscleton.sdk.*
import com.oscleton.sdk.internal.AppLifecycleObserver
import com.oscleton.sdk.internal.LiveSetDataManager
import com.oscleton.sdk.internal.MessageManager
import com.oscleton.sdk.internal.OSCManager
import org.koin.dsl.module.applicationContext

internal val oscletonSDKModule = applicationContext {
    bean { Configuration(get(), get()) }
    bean { Controller(get()) }
    bean { Receiver() }
    bean { ReactiveReceiver(get()) }
    bean { CallbackReceiver(get()) }
    bean { MessageManager(get()) }
    bean { OSCManager() }
    bean { LiveSetDataManager(get()) }
    bean { AppLifecycleObserver() }
}
