package com.oscleton.sdk.listeners;

import com.oscleton.sdk.models.DeviceParameter;

/**
 * Interface definition for a callback to be invoked
 * when a return device parameter changes.
 *
 * @since 0.9
 *
 * @deprecated Use the corresponding OnReturnDeviceParameterChangeListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.devices.listeners.OnReturnDeviceParameterChangeListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnReturnDeviceParameterChangeListener { })
 * </p>
 */
public interface OnReturnDeviceParameterChangeListener {
    void onReturnDeviceParameterChange(DeviceParameter deviceParameter);
}
