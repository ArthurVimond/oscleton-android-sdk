package com.oscleton.sdk.listeners;

/**
 * Interface definition for a callback to be invoked
 * when the computer IP discovery progress updates, from 0 to 1.
 *
 * @since 0.6
 */
public interface OnComputerIPDiscoveryProgressListener {
    void onComputerIPDiscoveryProgress(float progress);
}
