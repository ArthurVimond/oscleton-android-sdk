package com.oscleton.sdk.callbacks.tracks.listeners;

import com.oscleton.sdk.models.MasterParameter;

/**
 * Interface definition for a callback to be invoked
 * when a master parameter changes.
 *
 * @since 1.0
 */
public interface OnMasterParameterChangeListener {
    void onMasterParameterChange(MasterParameter masterParameter);
}