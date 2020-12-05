package com.oscleton.sdk.listeners;

/**
 * Interface definition for a callback to be invoked
 * when the mobile device is connected to the computer running Ableton Live.
 *
 * @since 0.2
 *
 * @deprecated Use the corresponding OnConnectionSuccessListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.configuration.listeners.OnConnectionSuccessListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnConnectionSuccessListener { })
 * </p>
 */
@Deprecated
public interface OnConnectionSuccessListener {
    void onConnectionSuccess();
}
