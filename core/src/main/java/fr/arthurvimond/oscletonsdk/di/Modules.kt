package fr.arthurvimond.oscletonsdk.di

import fr.arthurvimond.oscletonsdk.internal.AppLifecycleObserver
import org.koin.dsl.module.applicationContext

internal val oscletonSDKModule = applicationContext {
    bean { AppLifecycleObserver() }
}
