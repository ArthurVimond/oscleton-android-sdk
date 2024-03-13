package com.oscleton.sdk.models

/**
 * Data class representing Live track parameter indices.
 *
 * @constructor
 *
 * @property trackIndex The track position in the Live set
 * @property sendIndex The send index on the track
 *
 * @since 0.8
 */
internal data class SendIndices(
    val trackIndex: Int,
    val sendIndex: Int
)