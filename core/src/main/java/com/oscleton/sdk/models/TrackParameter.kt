package com.oscleton.sdk.models

import com.oscleton.sdk.enums.TrackParameterIndex

/**
 * Data class representing a Live track parameter.
 *
 * @constructor
 *
 * @property trackIndex The track position in the Live set
 * @property trackName The track name
 * @property paramIndex The track parameter index used internally
 * @property paramName The track parameter name
 * @property value The track parameter value
 * @property displayValue The track parameter display value
 * @property min The track parameter min value
 * @property max The track parameter max value
 *
 * @since 0.4
 */
data class TrackParameter(var trackIndex: Int,
                          var trackName: String = "",
                          var paramIndex: Int,
                          var value: Float = 0f,
                          var displayValue: String = "",
                          var min: Float = 0f,
                          var max: Float = 1f) {

    val paramName: String
        get() = when (paramIndex) {
            TrackParameterIndex.VOLUME -> "Volume"
            else -> ""
        }

}
