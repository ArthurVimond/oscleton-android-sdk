package com.oscleton.sdk.enums

/**
 * Constants class representing Live parameters which are emitted by
 * [ReactiveReceiver][com.oscleton.sdk.ReactiveReceiver] or [CallbackReceiver][com.oscleton.sdk.CallbackReceiver]
 * when changing in Ableton Live.
 *
 * Live parameters can be disabled or re-enabled by calling
 * [disableLiveParameters()][com.oscleton.sdk.Configuration.disableLiveParameters]
 * and [enableAllLiveParameters()][com.oscleton.sdk.Configuration.enableAllLiveParameters] respectively.
 *
 * This is useful in case of breaking changes due to Oscleton SDK / MIDI Remote Script version incompatibility
 * to avoid unstable SDK behaviors.
 *
 * All parameters are enabled by default.
 *
 * @since 0.5
 */
object LiveParameter {
    const val TRACK_VOLUME = "TRACK_VOLUME"
    const val TRACK_PANNING = "TRACK_PANNING"
    const val TRACK_MUTE = "TRACK_MUTE"
    const val TRACK_ARM = "TRACK_ARM"
    const val TRACK_SEND = "TRACK_SEND"
    const val DEVICE_PARAMETER = "DEVICE_PARAMETER"
}