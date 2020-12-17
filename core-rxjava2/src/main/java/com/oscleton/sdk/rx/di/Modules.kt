package com.oscleton.sdk.rx.di

import com.oscleton.sdk.rx.ConfigurationRx
import com.oscleton.sdk.rx.DevicesRx
import com.oscleton.sdk.rx.LiveSetRx
import com.oscleton.sdk.rx.TracksRx
import org.koin.dsl.module

internal val reactiveModule = module {
    single { ConfigurationRx(get()) }
    single { LiveSetRx(get()) }
    single { TracksRx(get()) }
    single { DevicesRx(get()) }
}