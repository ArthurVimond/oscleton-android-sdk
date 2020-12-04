package com.oscleton.sdk.callbacks.di

import com.oscleton.sdk.callbacks.ConfigurationCallbacks
import com.oscleton.sdk.callbacks.LiveSetCallbacks
import com.oscleton.sdk.callbacks.TracksCallbacks
import org.koin.dsl.module

internal val callbacksModule = module {
    single { ConfigurationCallbacks(get()) }
    single { LiveSetCallbacks(get()) }
    single { TracksCallbacks(get()) }
}