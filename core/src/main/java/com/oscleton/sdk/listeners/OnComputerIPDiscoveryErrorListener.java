package com.oscleton.sdk.listeners;

/**
 * Interface definition for a callback to be invoked
 * when the computer IP discovery failed to connect automatically
 * to the computer running Ableton Live.
 *
 * @since 0.6
 *
 * @deprecated Use the corresponding OnComputerIPDiscoveryErrorListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.configuration.listeners.OnComputerIPDiscoveryErrorListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnComputerIPDiscoveryErrorListener { })
 * </p>
 */
@Deprecated
public interface OnComputerIPDiscoveryErrorListener {
    void onComputerIPDiscoveryError(String message);
}
