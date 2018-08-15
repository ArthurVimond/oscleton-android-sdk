package fr.arthurvimond.oscletonsdk.sample.configuration

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import fr.arthurvimond.oscletonsdk.OscletonSDK

class ConfigurationViewModel : ViewModel() {

    val computerIPAddress: ObservableField<String> = ObservableField("")

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