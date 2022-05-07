package com.oscleton.sdk.tracks

import com.oscleton.sdk.enums.TrackParameterIndex
import com.oscleton.sdk.internal.LiveAPI
import com.oscleton.sdk.internal.MessageManager

/**
 * Tracks contains the methods related to the Live tracks.
 *
 * @since 1.0
 */
class Tracks internal constructor(
    private val tracksDataManager: TracksDataManager,
    private val messageManager: MessageManager
) {

    /**
     * Set a track parameter value.
     *
     * @param trackIndex the track index
     * @param trackParameterIndex the track parameter index
     * @param value the track parameter value to set
     * @since 1.1
     */
    fun setTrackParameter(trackIndex: Int, trackParameterIndex: Int, value: Float) {
        val address = when (trackParameterIndex) {
            TrackParameterIndex.VOLUME -> LiveAPI.trackVolume
            TrackParameterIndex.PANNING -> LiveAPI.trackPanning
            TrackParameterIndex.ARM -> LiveAPI.trackArm
            TrackParameterIndex.MUTE -> LiveAPI.trackMute
            TrackParameterIndex.SOLO -> LiveAPI.trackSolo
            else -> ""
        }
        messageManager.sendMessage(address, listOf(trackIndex, value))
    }

    /**
     * Set a track send value.
     *
     * @param trackIndex the track index
     * @param sendIndex the track send index
     * @param value the track send value to set
     * @since 1.1
     */
    fun setTrackSend(trackIndex: Int, sendIndex: Int, value: Float) {
        messageManager.sendMessage(LiveAPI.trackSend, listOf(trackIndex, sendIndex, value))
    }

    /**
     * Set a return track parameter value.
     *
     * @param trackIndex the return track index
     * @param trackParameterIndex the return track parameter index
     * @param value the return track parameter value to set
     * @since 1.1
     */
    fun setReturnParameter(trackIndex: Int, trackParameterIndex: Int, value: Float) {
        val address = when (trackParameterIndex) {
            TrackParameterIndex.VOLUME -> LiveAPI.returnVolume
            TrackParameterIndex.PANNING -> LiveAPI.returnPanning
            TrackParameterIndex.MUTE -> LiveAPI.returnMute
            TrackParameterIndex.SOLO -> LiveAPI.returnSolo
            else -> ""
        }
        messageManager.sendMessage(address, listOf(trackIndex, value))
    }

    /**
     * Set a master track parameter value.
     *
     * @param trackParameterIndex the parameter index
     * @param value the parameter value to set
     * @since 1.1
     */
    fun setMasterParameter(trackParameterIndex: Int, value: Float) {
        val address = when (trackParameterIndex) {
            TrackParameterIndex.VOLUME -> LiveAPI.masterVolume
            TrackParameterIndex.PANNING -> LiveAPI.masterPanning
            TrackParameterIndex.SOLO -> LiveAPI.masterSolo
            else -> ""
        }
        messageManager.sendMessage(address, listOf(0, value))
    }

}