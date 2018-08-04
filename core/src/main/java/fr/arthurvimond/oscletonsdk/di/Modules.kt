package fr.arthurvimond.oscletonsdk.di

import fr.arthurvimond.oscletonsdk.CallbackReceiver
import fr.arthurvimond.oscletonsdk.Controller
import fr.arthurvimond.oscletonsdk.ReactiveReceiver
import fr.arthurvimond.oscletonsdk.Receiver
import fr.arthurvimond.oscletonsdk.internal.*
import org.koin.dsl.module.applicationContext

internal val oscletonSDKModule = applicationContext {
    bean { Controller(get()) }
    bean { Receiver() }
    bean { ReactiveReceiver(get()) }
    bean { CallbackReceiver(get()) }
    bean { MessageManager(get(), get(), get()) }
    bean { OSCManager() }
    bean { MessageParser(get()) }
    bean { LiveSetDataManager() }
    bean { AppLifecycleObserver() }
}
