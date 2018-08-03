package fr.arthurvimond.oscletonsdk.di

import fr.arthurvimond.oscletonsdk.internal.*
import org.koin.dsl.module.applicationContext

internal val oscletonSDKModule = applicationContext {
    bean { AppLifecycleObserver() }
    bean { MessageManager(get(), get()) }
    bean { OSCManager() }
    bean { MessageParser(get()) }
    bean { LiveSetDataManager() }
}
