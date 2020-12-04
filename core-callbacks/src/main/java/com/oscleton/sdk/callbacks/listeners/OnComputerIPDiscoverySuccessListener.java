package com.oscleton.sdk.callbacks.listeners;

/**
 * Interface definition for a callback to be invoked
 * when the computer IP discovery succeeded to connect automatically
 * to the computer running Ableton Live.
 *
 * @since 1.0
 */
public interface OnComputerIPDiscoverySuccessListener {
    void onComputerIPDiscoverySuccess(String computerIP);
}
