package com.oscleton.sdk.sample.configuration

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.oscleton.sdk.OscletonSDK
import com.oscleton.sdk.utils.Empty
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

class ConfigurationViewModel : ViewModel() {

    // Public properties

    val computerIPAddress: ObservableField<String> = ObservableField("")

    val liveVersion: LiveData<String> = LiveDataReactiveStreams.fromPublisher(
            OscletonSDK.instance.config.liveVersion.toFlowable(BackpressureStrategy.LATEST))

    val scriptVersion: LiveData<String> = LiveDataReactiveStreams.fromPublisher(
            OscletonSDK.instance.config.scriptVersion.toFlowable(BackpressureStrategy.LATEST))

    val sdkVersion: ObservableField<String> = ObservableField(OscletonSDK.instance.config.sdkVersion)

    val onConnectionSuccess: Observable<Empty> = OscletonSDK.instance.config.onConnectionSuccess
    val onConnectionError: Observable<String> = OscletonSDK.instance.config.onConnectionError

    fun setComputerIPAddress() {
        val computerIP = computerIPAddress.get()
        computerIP?.let {
            if (!it.isEmpty()) {
                OscletonSDK.instance.config.setComputerIP(computerIP)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}