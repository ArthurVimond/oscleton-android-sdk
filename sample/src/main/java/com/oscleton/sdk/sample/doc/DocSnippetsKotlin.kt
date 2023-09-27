package com.oscleton.sdk.sample.doc

import com.oscleton.sdk.OscletonSDK
import com.oscleton.sdk.callbacks.cb
import com.oscleton.sdk.callbacks.configuration.listeners.OnComputerIPDiscoverySuccessListener
import com.oscleton.sdk.callbacks.devices.listeners.OnTrackDeviceParameterChangeListener
import com.oscleton.sdk.rx.rx

object DocSnippetsKotlin {

    fun snippet() {

        val config = OscletonSDK.instance.config
        val liveSet = OscletonSDK.instance.liveSet
        val tracks = OscletonSDK.instance.tracks
        val devices = OscletonSDK.instance.devices

        ////////////////////////////////
        // RxJava

        val configRx = config.rx()
        val liveSetRx = liveSet.rx()
        val tracksRx = tracks.rx()
        val devicesRx = devices.rx()

        ////////////////////////////////

        devicesRx.trackDeviceParameter
                .subscribe { trackDeviceParameter ->
                }

        ////////////////////////////////
        // Callbacks

        val configCallbacks = config.cb()
        val liveSetCallbacks = liveSet.cb()
        val tracksCallbacks = tracks.cb()
        val devicesCallbacks = devices.cb()

        ////////////////////////////////

        configCallbacks.set(OnComputerIPDiscoverySuccessListener {

        })

        devicesCallbacks.set(OnTrackDeviceParameterChangeListener { trackDeviceParameter ->

        })

    }

}