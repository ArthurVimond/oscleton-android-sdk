package com.oscleton.sdk.models

import com.oscleton.sdk.enums.ReturnParameterIndex

/**
 * Data class representing a Live return parameter.
 *
 * @constructor
 *
 * @property trackIndex The return position in the Live set
 * @property trackName The return name
 * @property paramIndex The return parameter index used internally
 * @property paramName The return parameter name
 * @property value The return parameter value
 * @property displayValue The return parameter display value
 * @property min The return parameter min value
 * @property max The return parameter max value
 *
 * @since 0.7
 */
data class ReturnParameter(var trackIndex: Int,
                           var trackName: String = "",
                           var paramIndex: Int,
                           var value: Float = 0f,
                           var displayValue: String = "",
                           var min: Float = 0f,
                           var max: Float = 1f) {

    val paramName: String
        get() = when (paramIndex) {
            ReturnParameterIndex.VOLUME -> "Volume"
            else -> ""
        }

}
