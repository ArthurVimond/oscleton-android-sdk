package com.oscleton.sdk.models

/**
 * Data class representing Live track parameter indices.
 *
 * @constructor
 *
 * @property trackIndex The track position in the Live set
 * @property paramIndex The parameter index used internally
 *
 * @since 0.4
 */
internal data class TrackParameterIndices(var trackIndex: Int,
                                          var paramIndex: Int)