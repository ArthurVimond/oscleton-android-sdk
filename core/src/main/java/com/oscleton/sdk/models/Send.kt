package com.oscleton.sdk.models

import com.oscleton.sdk.enums.AutomationState
import com.oscleton.sdk.enums.SendState
import com.oscleton.sdk.enums.SendType

/**
 * Data class representing a Live send.
 *
 * @constructor
 *
 * @property trackIndex The track position in the Live set
 * @property trackName The track name
 * @property sendIndex The send position in the track
 * @property sendName The send name
 * @property sendType The send type, either TRACK or RETURN
 * @property volume The send volume value
 * @property displayVolume The send display volume
 *
 * @since 0.8
 */
data class Send(
    val trackIndex: Int,
    val trackName: String = "",
    val sendIndex: Int,
    val sendName: String = "",
    val sendType: SendType = SendType.TRACK,
    val volume: Float = 0f,
    val displayVolume: String = "",
    val sendState: SendState = SendState.ENABLED,
    val automationState: AutomationState = AutomationState.NONE
)