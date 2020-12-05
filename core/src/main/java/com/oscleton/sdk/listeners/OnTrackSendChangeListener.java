package com.oscleton.sdk.listeners;

import com.oscleton.sdk.models.Send;

/**
 * Interface definition for a callback to be invoked
 * when a track send changes.
 *
 * @since 0.8
 *
 * @deprecated Use the corresponding OnTrackSendChangeListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.tracks.listeners.OnTrackSendChangeListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnTrackSendChangeListener { })
 * </p>
 */
@Deprecated
public interface OnTrackSendChangeListener {
    void onTrackSendChange(Send trackSend);
}