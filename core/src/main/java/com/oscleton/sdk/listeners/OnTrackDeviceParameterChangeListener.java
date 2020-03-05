package com.oscleton.sdk.listeners;

import com.oscleton.sdk.models.DeviceParameter;

/**
 * Interface definition for a callback to be invoked
 * when a track device parameter changes.
 *
 * @since 0.8
 */
public interface OnTrackDeviceParameterChangeListener {
    void onTrackDeviceParameterChange(DeviceParameter deviceParameter);
}
