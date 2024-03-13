package com.oscleton.sdk.callbacks.tracks.listeners;

import com.oscleton.sdk.models.Send;

/**
 * Interface definition for a callback to be invoked
 * when a return track send changes.
 *
 * @since 1.2
 */
public interface OnReturnSendChangeListener {
    void onReturnSendChange(Send returnSend);
}