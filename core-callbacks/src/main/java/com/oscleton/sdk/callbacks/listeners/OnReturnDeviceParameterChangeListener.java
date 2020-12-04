package com.oscleton.sdk.callbacks.listeners;

import com.oscleton.sdk.models.DeviceParameter;

/**
 * Interface definition for a callback to be invoked
 * when a return device parameter changes.
 *
 * @since 1.0
 */
public interface OnReturnDeviceParameterChangeListener {
    void onReturnDeviceParameterChange(DeviceParameter deviceParameter);
}
