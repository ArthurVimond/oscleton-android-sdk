package com.oscleton.sdk.callbacks.di

import com.oscleton.sdk.callbacks.configuration.ConfigurationCallbacks
import com.oscleton.sdk.callbacks.liveset.LiveSetCallbacks
import com.oscleton.sdk.callbacks.tracks.TracksCallbacks
import org.koin.dsl.module

internal val callbacksModule = module {
    single { ConfigurationCallbacks(get()) }
    single { LiveSetCallbacks(get()) }
    single { TracksCallbacks(get()) }
}