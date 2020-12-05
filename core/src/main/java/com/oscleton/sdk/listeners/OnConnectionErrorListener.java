package com.oscleton.sdk.listeners;

/**
 * Interface definition for a callback to be invoked
 * when the connection to the computer running Ableton Live failed.
 *
 * @since 0.6
 *
 * @deprecated Use the corresponding OnConnectionErrorListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.configuration.listeners.OnConnectionErrorListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnConnectionErrorListener { })
 * </p>
 */
@Deprecated
public interface OnConnectionErrorListener {
    void onConnectionError(String message);
}
