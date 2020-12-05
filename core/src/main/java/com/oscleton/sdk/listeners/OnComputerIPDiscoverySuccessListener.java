package com.oscleton.sdk.listeners;

/**
 * Interface definition for a callback to be invoked
 * when the computer IP discovery succeeded to connect automatically
 * to the computer running Ableton Live.
 *
 * @since 0.6
 *
 * @deprecated Use the corresponding OnComputerIPDiscoverySuccessListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.configuration.listeners.OnComputerIPDiscoverySuccessListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnComputerIPDiscoverySuccessListener { })
 * </p>
 */
@Deprecated
public interface OnComputerIPDiscoverySuccessListener {
    void onComputerIPDiscoverySuccess(String computerIP);
}
