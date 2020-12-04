package com.oscleton.sdk.rx

import com.oscleton.sdk.devices.DevicesDataManager
import com.oscleton.sdk.models.DeviceParameter
import io.reactivex.Observable

class DevicesRx internal constructor(devicesDataManager: DevicesDataManager) {

    /**
     * Returns the last changing track device parameter as Observable.
     *
     * @return the last changing track device parameter as Observable
     * @since 1.0
     */
    val trackDeviceParameter: Observable<DeviceParameter> = devicesDataManager.trackDeviceParameter

    /**
     * Returns the last changing return device parameter as Observable.
     *
     * @return the last changing return device parameter as Observable
     * @since 1.0
     */
    val returnDeviceParameter: Observable<DeviceParameter> = devicesDataManager.returnDeviceParameter

    /**
     * Returns the last changing master device parameter as Observable.
     *
     * @return the last changing master device parameter as Observable
     * @since 1.0
     */
    val masterDeviceParameter: Observable<DeviceParameter> = devicesDataManager.masterDeviceParameter

}