package com.oscleton.sdk.callbacks.configuration.listeners;

/**
 * Interface definition for a callback to be invoked
 * when the connection to the computer running Ableton Live failed.
 *
 * @since 1.0
 */
public interface OnConnectionErrorListener {
    void onConnectionError(String message);
}
