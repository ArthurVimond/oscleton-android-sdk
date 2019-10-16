package com.oscleton.sdk.sample.controller

import androidx.lifecycle.ViewModel
import com.oscleton.sdk.OscletonSDK

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

    fun undo() {
        OscletonSDK.instance.controller.undo()
    }

    fun redo() {
        OscletonSDK.instance.controller.redo()
    }

    override fun onCleared() {
        super.onCleared()
    }
}