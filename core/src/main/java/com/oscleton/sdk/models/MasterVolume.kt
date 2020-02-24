package com.oscleton.sdk.models

/**
 * Data class representing the Live master volume.
 *
 * @constructor
 *
 * @property volume The master volume value
 * @property displayVolume The master display volume
 *
 * @since 0.7
 */
internal data class MasterVolume(var volume: Float,
                                 var displayVolume: String)