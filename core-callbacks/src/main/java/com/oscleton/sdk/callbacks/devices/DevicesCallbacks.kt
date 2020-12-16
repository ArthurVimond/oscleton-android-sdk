package com.oscleton.sdk.callbacks.devices

import com.oscleton.sdk.callbacks.devices.listeners.OnMasterDeviceParameterChangeListener
import com.oscleton.sdk.callbacks.devices.listeners.OnReturnDeviceParameterChangeListener
import com.oscleton.sdk.callbacks.devices.listeners.OnTrackDeviceParameterChangeListener
import com.oscleton.sdk.devices.DevicesDataManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * DevicesCallbacks contains callbacks triggered by the Live devices changes.
 *
 * If you prefer using a reactive approach, consider using `DevicesRx` from the 'core-rxjava2' artifact instead.
 *
 * @since 1.0
 */
class DevicesCallbacks internal constructor(private val devicesDataManager: DevicesDataManager) {

    // Rx
    private val compositeDisposable = CompositeDisposable()
    private var trackDeviceParameterDisp: Disposable? = null
    private var returnDeviceParameterDisp: Disposable? = null
    private var masterDeviceParameterDisp: Disposable? = null

    /**
     * Register a callback to be invoked when track device parameter changes.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
    fun set(listener: OnTrackDeviceParameterChangeListener) {
        trackDeviceParameterDisp = devicesDataManager.trackDeviceParameter
                .subscribe {
                    listener.onTrackDeviceParameterChange(it)
                }

        compositeDisposable.add(trackDeviceParameterDisp!!)
    }

    /**
     * Remove the current [OnTrackDeviceParameterChangeListener].
     *
     * @since 1.0
     */
    fun removeOnTrackDeviceParameterChangeListener() {
        trackDeviceParameterDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when return device parameter changes.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
    fun set(listener: OnReturnDeviceParameterChangeListener) {
        returnDeviceParameterDisp = devicesDataManager.returnDeviceParameter
                .subscribe {
                    listener.onReturnDeviceParameterChange(it)
                }

        compositeDisposable.add(returnDeviceParameterDisp!!)
    }

    /**
     * Remove the current [OnReturnDeviceParameterChangeListener].
     *
     * @since 1.0
     */
    fun removeOnReturnDeviceParameterChangeListener() {
        returnDeviceParameterDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when master device parameter changes.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
    fun set(listener: OnMasterDeviceParameterChangeListener) {
        masterDeviceParameterDisp = devicesDataManager.masterDeviceParameter
                .subscribe {
                    listener.onMasterDeviceParameterChange(it)
                }

        compositeDisposable.add(returnDeviceParameterDisp!!)
    }

    /**
     * Remove the current [OnMasterDeviceParameterChangeListener].
     *
     * @since 1.0
     */
    fun removeOnMasterDeviceParameterChangeListener() {
        masterDeviceParameterDisp?.dispose()
    }

    /**
     * Remove all listeners.
     *
     * @since 1.0
     */
    fun removeAllListeners() {
        compositeDisposable.clear()
    }

}