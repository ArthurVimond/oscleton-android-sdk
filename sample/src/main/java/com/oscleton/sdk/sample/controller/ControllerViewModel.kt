package com.oscleton.sdk.sample.controller

import androidx.lifecycle.ViewModel
import com.oscleton.sdk.OscletonSDK

class ControllerViewModel : ViewModel() {

    fun play() {
        OscletonSDK.instance.liveSet.play()
    }

    fun stop() {
        OscletonSDK.instance.liveSet.stop()
    }

    fun setMetronome(enabled: Boolean) {
        OscletonSDK.instance.liveSet.setMetronome(enabled)
    }

    fun setOverdub(enabled: Boolean) {
        OscletonSDK.instance.liveSet.setOverdub(enabled)
    }

    fun undo() {
        OscletonSDK.instance.liveSet.undo()
    }

    fun redo() {
        OscletonSDK.instance.liveSet.redo()
    }

    override fun onCleared() {
        super.onCleared()
    }
}