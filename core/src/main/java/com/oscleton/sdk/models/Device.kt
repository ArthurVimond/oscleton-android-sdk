package com.oscleton.sdk.models

/**
 * Data class representing a Live device.
 *
 * @constructor
 *
 * @property trackIndex The track position in the Live set
 * @property deviceIndex The device position in the track
 * @property name The device name
 *
 * @since 0.1
 */
data class Device(
    val trackIndex: Int,
    val deviceIndex: Int,
    val name: String = ""
)