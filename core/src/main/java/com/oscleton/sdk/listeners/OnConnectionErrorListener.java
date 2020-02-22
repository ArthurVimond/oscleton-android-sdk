package com.oscleton.sdk.listeners;

/**
 * Interface definition for a callback to be invoked
 * when the connection to the computer running Ableton Live failed.
 *
 * @since 0.6
 */
public interface OnConnectionErrorListener {
    void onConnectionError(String message);
}
