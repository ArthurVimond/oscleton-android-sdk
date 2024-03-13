package com.oscleton.sdk.models

/**
 * Data class representing a Live device parameter indices.
 *
 * @constructor
 *
 * @property trackIndex The track position in the Live set
 * @property deviceIndex The device position in the track
 * @property paramIndex The parameter position on the device
 *
 * @since 0.1
 */
internal data class DeviceParameterIndices(
    val trackIndex: Int,
    val deviceIndex: Int,
    val paramIndex: Int
)