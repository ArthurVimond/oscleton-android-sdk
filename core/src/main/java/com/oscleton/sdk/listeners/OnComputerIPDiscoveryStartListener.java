package com.oscleton.sdk.listeners;

/**
 * Interface definition for a callback to be invoked
 * when the computer IP discovery starts in order to connect automatically
 * to the computer running Ableton Live.
 *
 * @since 0.6
 *
 * @deprecated Use the corresponding OnComputerIPDiscoveryStartListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.configuration.listeners.OnComputerIPDiscoveryStartListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnComputerIPDiscoveryStartListener { })
 * </p>
 */
@Deprecated
public interface OnComputerIPDiscoveryStartListener {
    void onComputerIPDiscoveryStart();
}
