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

    override fun onCleared() {
        super.onCleared()
    }
}