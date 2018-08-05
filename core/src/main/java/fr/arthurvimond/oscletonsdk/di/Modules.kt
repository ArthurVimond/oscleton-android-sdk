package fr.arthurvimond.oscletonsdk.di

import fr.arthurvimond.oscletonsdk.*
import fr.arthurvimond.oscletonsdk.internal.*
import org.koin.dsl.module.applicationContext

internal val oscletonSDKModule = applicationContext {
    bean { Configuration(get()) }
    bean { Controller(get()) }
    bean { Receiver() }
    bean { ReactiveReceiver(get()) }
    bean { CallbackReceiver(get()) }
    bean { MessageManager(get(), get(), get()) }
    bean { OSCManager() }
    bean { LiveSetDataManager() }
    bean { AppLifecycleObserver() }
}
