package com.oscleton.sdk.listeners;

import com.oscleton.sdk.models.MasterParameter;

/**
 * Interface definition for a callback to be invoked
 * when a master parameter changes.
 *
 * @since 0.7
 */
public interface OnMasterParameterChangeListener {
    void onMasterParameterChange(MasterParameter masterParameter);
}