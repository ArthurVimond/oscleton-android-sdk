package com.oscleton.sdk.listeners;

import com.oscleton.sdk.models.MasterParameter;

/**
 * Interface definition for a callback to be invoked
 * when a master parameter changes.
 *
 * @since 0.7
 *
 * @deprecated Use the corresponding OnMasterParameterChangeListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.tracks.listeners.OnMasterParameterChangeListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnMasterParameterChangeListener { })
 * </p>
 */
@Deprecated
public interface OnMasterParameterChangeListener {
    void onMasterParameterChange(MasterParameter masterParameter);
}