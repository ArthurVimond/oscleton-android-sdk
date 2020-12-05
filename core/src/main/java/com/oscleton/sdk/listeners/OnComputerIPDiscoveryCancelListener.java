package com.oscleton.sdk.listeners;

/**
 * Interface definition for a callback to be invoked
 * when the computer IP discovery is cancelled.
 *
 * @since 0.7
 *
 * @deprecated Use the corresponding OnComputerIPDiscoveryCancelListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.configuration.listeners.OnComputerIPDiscoveryCancelListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnComputerIPDiscoveryCancelListener { })
 * </p>
 */
@Deprecated
public interface OnComputerIPDiscoveryCancelListener {
    void onComputerIPDiscoveryCancel();
}
