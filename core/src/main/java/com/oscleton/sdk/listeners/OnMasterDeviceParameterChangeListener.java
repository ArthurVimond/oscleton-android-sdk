package com.oscleton.sdk.listeners;

import com.oscleton.sdk.models.DeviceParameter;

/**
 * Interface definition for a callback to be invoked
 * when a master device parameter changes.
 *
 * @since 0.9
 *
 * @deprecated Use the corresponding OnMasterDeviceParameterChangeListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.devices.listeners.OnMasterDeviceParameterChangeListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnMasterDeviceParameterChangeListener { })
 * </p>
 */
@Deprecated
public interface OnMasterDeviceParameterChangeListener {
    void onMasterDeviceParameterChange(DeviceParameter deviceParameter);
}
