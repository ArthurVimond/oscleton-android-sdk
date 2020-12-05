package com.oscleton.sdk.listeners;

/**
 * Interface definition for a callback to be invoked
 * when the live set tempo changes.
 *
 * @since 0.1
 *
 * @deprecated Use the corresponding OnTempoChangeListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.liveset.listeners.OnTempoChangeListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnTempoChangeListener { })
 * </p>
 */
@Deprecated
public interface OnTempoChangeListener {
    void onTempoChange(float tempo);
}
