package com.oscleton.sdk.models

/**
 * Data class representing a Live track volume.
 *
 * @constructor
 *
 * @property trackIndex The track position in the Live set
 * @property trackName The track name
 * @property volume The track volume value
 * @property displayVolume The track display volume
 *
 * @since 0.4
 */
internal data class TrackVolume(var trackIndex: Int,
                       var trackName: String = "",
                       var volume: Float,
                       var displayVolume: String)