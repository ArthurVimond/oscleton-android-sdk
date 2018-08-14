package fr.arthurvimond.oscletonsdk.sample.controller

import android.arch.lifecycle.ViewModel
import fr.arthurvimond.oscletonsdk.OscletonSDK

class ControllerViewModel : ViewModel() {

    fun play() {
        OscletonSDK.instance.controller.play()
    }

    fun stop() {
        OscletonSDK.instance.controller.stop()
    }

    fun setMetronome(enabled: Boolean) {
        OscletonSDK.instance.controller.setMetronome(enabled)
    }

    fun setOverdub(enabled: Boolean) {
        OscletonSDK.instance.controller.setOverdub(enabled)
    }

    override fun onCleared() {
        super.onCleared()
    }
}