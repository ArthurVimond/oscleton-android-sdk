package fr.arthurvimond.oscletonsdk.di

import fr.arthurvimond.oscletonsdk.internal.AppLifecycleObserver
import fr.arthurvimond.oscletonsdk.internal.LiveSetDataManager
import org.koin.dsl.module.applicationContext

internal val oscletonSDKModule = applicationContext {
    bean { AppLifecycleObserver() }
    bean { LiveSetDataManager() }
}
