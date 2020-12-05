package com.oscleton.sdk.callbacks.configuration.listeners;

/**
 * Interface definition for a callback to be invoked
 * when the computer IP discovery failed to connect automatically
 * to the computer running Ableton Live.
 *
 * @since 1.0
 */
public interface OnComputerIPDiscoveryErrorListener {
    void onComputerIPDiscoveryError(String message);
}
