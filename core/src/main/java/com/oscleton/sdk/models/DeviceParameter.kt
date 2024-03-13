package com.oscleton.sdk.models

import com.oscleton.sdk.enums.AutomationState

/**
 * Data class representing a Live device parameter.
 *
 * @constructor
 *
 * @property trackIndex The track position in the Live set
 * @property deviceIndex The device position in the track
 * @property paramIndex The parameter position on the device
 * @property trackName The track name containing the device
 * @property deviceName The device name containing the parameter
 * @property paramName The parameter name
 * @property displayValue The parameter display value
 * @property value The parameter value
 * @property min The parameter min value
 * @property max The parameter max value
 *
 * @since 0.1
 */
data class DeviceParameter(
    val trackIndex: Int,
    val deviceIndex: Int,
    val paramIndex: Int,
    val trackName: String = "",
    val deviceName: String = "",
    val paramName: String = "",
    val displayValue: String = "",
    val value: Float = 0f,
    val min: Float = 0f,
    val max: Float = 1f,
    val automationState: AutomationState = AutomationState.NONE
)