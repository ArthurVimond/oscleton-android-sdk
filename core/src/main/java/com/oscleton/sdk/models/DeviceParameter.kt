package com.oscleton.sdk.models

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
data class DeviceParameter(var trackIndex: Int,
                           var deviceIndex: Int,
                           var paramIndex: Int,
                           var trackName: String = "",
                           var deviceName: String = "",
                           var paramName: String = "",
                           var displayValue: String = "",
                           var value: Float = 0f,
                           var min: Float = 0f,
                           var max: Float = 1f)