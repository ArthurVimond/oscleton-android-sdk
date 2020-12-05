package com.oscleton.sdk

import com.oscleton.sdk.devices.DevicesDataManager
import com.oscleton.sdk.liveset.LiveSetDataManager
import com.oscleton.sdk.models.*
import com.oscleton.sdk.tracks.TracksDataManager
import io.reactivex.Observable

/**
 * ReactiveReceiver contains RxJava Observables reacting to the Live set data changes.
 *
 * If a reactive approach doesn't fit your needs, consider implementing [CallbackReceiver] instead.
 *
 * @since 0.1
 * @see CallbackReceiver
 */
@Deprecated(message = "Use corresponding rx() extension functions from the 'core-rxjava2' artifact instead")
class ReactiveReceiver internal constructor(liveSetDataManager: LiveSetDataManager,
                                            tracksDataManager: TracksDataManager,
                                            devicesDataManager: DevicesDataManager) {

    /**
     * Returns the Live set global tempo as Observable.
     *
     * @return the Live set global tempo as Observable
     * @since 0.1
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.liveSet.rx().tempo instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.liveSet.rx().tempo"))
    val tempo: Observable<Float> = liveSetDataManager.tempo

    /**
     * Indicates if Live can capture MIDI as Observable.
     *
     * @return indicates if Live can capture MIDI as Observable
     * @since 1.0
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.liveSet.rx().canCaptureMidi instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.liveSet.rx().canCaptureMidi"))
    val canCaptureMidi: Observable<Boolean> = liveSetDataManager.canCaptureMidi

    /**
     * Returns the last changing track device parameter as Observable.
     *
     * @return the last changing track device parameter as Observable
     * @since 0.8
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.devices.rx().trackDeviceParameter instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.devices.rx().trackDeviceParameter"))
    val trackDeviceParameter: Observable<DeviceParameter> = devicesDataManager.trackDeviceParameter

    /**
     * Returns the last changing track parameter as Observable.
     *
     * @return the last changing track parameter as Observable
     * @since 0.4
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.tracks.rx().trackParameter instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.tracks.rx().trackParameter"))
    val trackParameter: Observable<TrackParameter> = tracksDataManager.trackParameter

    /**
     * Returns the last changing track send as Observable.
     *
     * @return the last changing track send as Observable
     * @since 0.8
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.tracks.rx().trackSend instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.tracks.rx().trackSend"))
    val trackSend: Observable<Send> = tracksDataManager.trackSend

    /**
     * Returns the last changing return device parameter as Observable.
     *
     * @return the last changing return device parameter as Observable
     * @since 0.9
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.devices.rx().returnDeviceParameter instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.devices.rx().returnDeviceParameter"))
    val returnDeviceParameter: Observable<DeviceParameter> = devicesDataManager.returnDeviceParameter

    /**
     * Returns the last changing return parameter as Observable.
     *
     * @return the last changing return parameter as Observable
     * @since 0.7
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.tracks.rx().returnParameter instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.tracks.rx().returnParameter"))
    val returnParameter: Observable<ReturnParameter> = tracksDataManager.returnParameter

    /**
     * Returns the last changing master parameter as Observable.
     *
     * @return the last changing master parameter as Observable
     * @since 0.7
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.tracks.rx().masterParameter instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.tracks.rx().masterParameter"))
    val masterParameter: Observable<MasterParameter> = tracksDataManager.masterParameter

    /**
     * Returns the last changing master device parameter as Observable.
     *
     * @return the last changing master device parameter as Observable
     * @since 0.9
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.devices.rx().masterDeviceParameter instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.devices.rx().masterDeviceParameter"))
    val masterDeviceParameter: Observable<DeviceParameter> = devicesDataManager.masterDeviceParameter

}