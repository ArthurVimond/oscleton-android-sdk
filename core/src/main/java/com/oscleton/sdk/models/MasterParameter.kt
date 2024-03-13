package com.oscleton.sdk.models

import com.oscleton.sdk.enums.MasterParameterIndex

/**
 * Data class representing a Live master parameter.
 *
 * @constructor
 *
 * @property paramIndex The master parameter index used internally
 * @property paramName The master parameter name
 * @property value The master parameter value
 * @property displayValue The master parameter display value
 * @property min The master parameter min value
 * @property max The master parameter max value
 *
 * @since 0.7
 */
data class MasterParameter(
    val paramIndex: Int,
    val value: Float = 0f,
    val displayValue: String = "",
    val min: Float = 0f,
    val max: Float = 1f
) {

    val paramName: String
        get() = when (paramIndex) {
            MasterParameterIndex.VOLUME -> "Volume"
            else -> ""
        }

}
