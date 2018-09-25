package com.oscleton.sdk.models

import com.oscleton.sdk.enums.TrackType

/**
 * Data class representing a Live track.
 *
 * @constructor
 *
 * @property index The track position in the Live set
 * @property name The track name
 * @property type The track type
 * @property volume The track volume
 * @property pan The track panning
 * @property arm The track arm state
 * @property mute The track mute state
 * @property solo The track solo state
 *
 * @since 0.1
 */
data class Track(var index: Int,
                 var name: String = "",
                 var type: TrackType = TrackType.AUDIO,
                 var volume: Float = 0.85f,
                 var pan: Float = 0f,
                 var arm: Int = 0,
                 var mute: Int = 0,
                 var solo: Int = 0)