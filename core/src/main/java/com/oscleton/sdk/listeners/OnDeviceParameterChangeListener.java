package com.oscleton.sdk.listeners;

import com.oscleton.sdk.models.DeviceParameter;

/**
 * Interface definition for a callback to be invoked
 * when a device parameter changes.
 *
 * @deprecated use {@link OnTrackDeviceParameterChangeListener} instead.
 * @since 0.1
 */
@Deprecated()
public interface OnDeviceParameterChangeListener {
    void onDeviceParameterChange(DeviceParameter deviceParameter);
}
