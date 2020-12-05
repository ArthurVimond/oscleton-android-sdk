package com.oscleton.sdk.listeners;

/**
 * Interface definition for a callback to be invoked
 * when Ableton Live starts.
 *
 * @since 0.6
 * @deprecated Use the corresponding OnStartListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.configuration.listeners.OnStartListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnStartListener { })
 * </p>
 */
@Deprecated
public interface OnStartListener {
    void onStart();
}
