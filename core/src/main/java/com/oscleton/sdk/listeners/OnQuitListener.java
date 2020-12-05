package com.oscleton.sdk.listeners;

/**
 * Interface definition for a callback to be invoked
 * when Ableton Live quits.
 *
 * @since 0.6
 *
 * @deprecated Use the corresponding OnQuitListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.configuration.listeners.OnQuitListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnQuitListener { })
 * </p>
 */
@Deprecated
public interface OnQuitListener {
    void onQuit();
}
