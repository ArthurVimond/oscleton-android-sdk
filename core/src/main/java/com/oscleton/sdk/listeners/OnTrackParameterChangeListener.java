package com.oscleton.sdk.listeners;

import com.oscleton.sdk.models.TrackParameter;

/**
 * Interface definition for a callback to be invoked
 * when a track parameter changes.
 *
 * @since 0.4
 *
 * @deprecated Use the corresponding OnTrackParameterChangeListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.tracks.listeners.OnTrackParameterChangeListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnTrackParameterChangeListener { })
 * </p>
 */
@Deprecated
public interface OnTrackParameterChangeListener {
    void onTrackParameterChange(TrackParameter trackParameter);
}