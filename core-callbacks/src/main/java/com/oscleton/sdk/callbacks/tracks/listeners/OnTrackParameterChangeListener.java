package com.oscleton.sdk.callbacks.tracks.listeners;

import com.oscleton.sdk.models.TrackParameter;

/**
 * Interface definition for a callback to be invoked
 * when a track parameter changes.
 *
 * @since 1.0
 */
public interface OnTrackParameterChangeListener {
    void onTrackParameterChange(TrackParameter trackParameter);
}