package com.oscleton.sdk.callbacks.devices.listeners;

import com.oscleton.sdk.models.DeviceParameter;

/**
 * Interface definition for a callback to be invoked
 * when a master device parameter changes.
 *
 * @since 1.0
 */
public interface OnMasterDeviceParameterChangeListener {
    void onMasterDeviceParameterChange(DeviceParameter deviceParameter);
}
