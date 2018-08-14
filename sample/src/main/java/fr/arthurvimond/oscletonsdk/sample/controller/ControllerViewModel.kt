package fr.arthurvimond.oscletonsdk.sample.controller

import android.arch.lifecycle.ViewModel
import fr.arthurvimond.oscletonsdk.OscletonSDK
import fr.arthurvimond.oscletonsdk.sample.utils.Logger

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

    override fun onCleared() {
        super.onCleared()
    }
}