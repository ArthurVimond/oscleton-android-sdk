package com.oscleton.sdk.callbacks.listeners;

import com.oscleton.sdk.models.DeviceParameter;

/**
 * Interface definition for a callback to be invoked
 * when a track device parameter changes.
 *
 * @since 1.0
 */
public interface OnTrackDeviceParameterChangeListener {
    void onTrackDeviceParameterChange(DeviceParameter deviceParameter);
}
