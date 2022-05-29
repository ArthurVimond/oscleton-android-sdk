package com.oscleton.sdk.sample.controller

import androidx.lifecycle.ViewModel
import com.oscleton.sdk.OscletonSDK
import com.oscleton.sdk.enums.TrackParameterIndex

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

    fun setTrackVolume() {
        OscletonSDK.instance.tracks.setTrackParameter(
            trackIndex = 0,
            trackParameterIndex = TrackParameterIndex.VOLUME,
            value = 0.5f
        )
    }

    override fun onCleared() {
        super.onCleared()
    }
}