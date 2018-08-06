package fr.arthurvimond.oscletonsdk.listeners;

import fr.arthurvimond.oscletonsdk.models.DeviceParameter;

/**
 * Interface definition for a callback to be invoked
 * when a device parameter changes.
 *
 * @since 0.1
 */
public interface OnDeviceParameterChangeListener {
    void onDeviceParameterChange(DeviceParameter deviceParameter);
}
