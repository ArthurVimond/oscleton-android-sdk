package com.oscleton.sdk.listeners;

import com.oscleton.sdk.models.DeviceParameter;

/**
 * Interface definition for a callback to be invoked
 * when a track device parameter changes.
 *
 * @since 0.8
 *
 * @deprecated Use the corresponding OnTrackDeviceParameterChangeListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.devices.listeners.OnTrackDeviceParameterChangeListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnTrackDeviceParameterChangeListener { })
 * </p>
 */
@Deprecated
public interface OnTrackDeviceParameterChangeListener {
    void onTrackDeviceParameterChange(DeviceParameter deviceParameter);
}
