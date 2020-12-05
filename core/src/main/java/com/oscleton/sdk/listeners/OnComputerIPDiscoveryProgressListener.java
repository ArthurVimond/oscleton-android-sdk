package com.oscleton.sdk.listeners;

/**
 * Interface definition for a callback to be invoked
 * when the computer IP discovery progress updates, from 0 to 1.
 *
 * @since 0.6
 *
 * @deprecated Use the corresponding OnComputerIPDiscoveryProgressListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.configuration.listeners.OnComputerIPDiscoveryProgressListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnComputerIPDiscoveryProgressListener { })
 * </p>
 */
@Deprecated
public interface OnComputerIPDiscoveryProgressListener {
    void onComputerIPDiscoveryProgress(float progress);
}
