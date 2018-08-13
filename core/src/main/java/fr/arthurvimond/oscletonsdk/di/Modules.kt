package fr.arthurvimond.oscletonsdk.di

import fr.arthurvimond.oscletonsdk.*
import fr.arthurvimond.oscletonsdk.internal.AppLifecycleObserver
import fr.arthurvimond.oscletonsdk.internal.LiveSetDataManager
import fr.arthurvimond.oscletonsdk.internal.MessageManager
import fr.arthurvimond.oscletonsdk.internal.OSCManager
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
