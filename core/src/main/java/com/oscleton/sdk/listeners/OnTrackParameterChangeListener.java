package com.oscleton.sdk.listeners;

import com.oscleton.sdk.models.TrackParameter;

/**
 * Interface definition for a callback to be invoked
 * when a track parameter changes.
 *
 * @since 0.4
 */
public interface OnTrackParameterChangeListener {
    void onTrackParameterChange(TrackParameter trackParameter);
}