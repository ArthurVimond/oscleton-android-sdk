package com.oscleton.sdk.devices

import com.oscleton.sdk.internal.LiveAPI
import com.oscleton.sdk.internal.MessageManager

/**
 * Devices contains the methods related to the Live devices.
 *
 * @since 1.0
 */
class Devices internal constructor(private val devicesDataManager: DevicesDataManager,
                                  private val messageManager: MessageManager) {

    /**
     * Set a track device parameter value.
     *
     * @param trackIndex the track index
     * @param deviceIndex the device index
     * @param paramIndex the parameter index
     * @param value the parameter value to set
     * @since 1.1
     */
    fun setTrackDeviceParameter(trackIndex: Int, deviceIndex: Int, paramIndex: Int, value: Float) {
        messageManager.sendMessage(LiveAPI.trackDeviceParam, listOf(trackIndex, deviceIndex, paramIndex, value))
    }

    /**
     * Set a return track device parameter value.
     *
     * @param trackIndex the return track index
     * @param deviceIndex the device index
     * @param paramIndex the parameter index
     * @param value the parameter value to set
     * @since 1.1
     */
    fun setReturnDeviceParameter(trackIndex: Int, deviceIndex: Int, paramIndex: Int, value: Float) {
        messageManager.sendMessage(LiveAPI.returnDeviceParam, listOf(trackIndex, deviceIndex, paramIndex, value))
    }

    /**
     * Set a master track device parameter value.
     *
     * @param deviceIndex the device index
     * @param paramIndex the parameter index
     * @param value the parameter value to set
     * @since 1.1
     */
    fun setMasterDeviceParameter(deviceIndex: Int, paramIndex: Int, value: Float) {
        messageManager.sendMessage(LiveAPI.masterDeviceParam, listOf(0, deviceIndex, paramIndex, value))
    }

}