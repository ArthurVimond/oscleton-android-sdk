package com.oscleton.sdk.listeners;

import com.oscleton.sdk.models.DeviceParameter;

/**
 * Interface definition for a callback to be invoked
 * when a master device parameter changes.
 *
 * @since 0.9
 */
public interface OnMasterDeviceParameterChangeListener {
    void onMasterDeviceParameterChange(DeviceParameter deviceParameter);
}
