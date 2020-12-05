package com.oscleton.sdk.callbacks.tracks.listeners;

import com.oscleton.sdk.models.Send;

/**
 * Interface definition for a callback to be invoked
 * when a track send changes.
 *
 * @since 1.0
 */
public interface OnTrackSendChangeListener {
    void onTrackSendChange(Send trackSend);
}