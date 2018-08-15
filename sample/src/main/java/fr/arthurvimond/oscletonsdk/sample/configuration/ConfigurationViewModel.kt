package fr.arthurvimond.oscletonsdk.sample.configuration

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import fr.arthurvimond.oscletonsdk.OscletonSDK
import io.reactivex.BackpressureStrategy

class ConfigurationViewModel : ViewModel() {

    val computerIPAddress: ObservableField<String> = ObservableField("")

    val liveVersion: LiveData<String> = LiveDataReactiveStreams.fromPublisher(
            OscletonSDK.instance.config.liveVersion.toFlowable(BackpressureStrategy.LATEST))

    val scriptVersion: LiveData<String> = LiveDataReactiveStreams.fromPublisher(
            OscletonSDK.instance.config.scriptVersion.toFlowable(BackpressureStrategy.LATEST))

    val sdkVersion: ObservableField<String> = ObservableField(OscletonSDK.instance.config.sdkVersion)

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