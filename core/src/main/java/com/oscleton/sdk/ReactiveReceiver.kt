package com.oscleton.sdk

import com.oscleton.sdk.internal.LiveSetDataManager
import com.oscleton.sdk.models.DeviceParameter
import com.oscleton.sdk.models.MasterParameter
import com.oscleton.sdk.models.ReturnParameter
import com.oscleton.sdk.models.TrackParameter
import io.reactivex.Observable

/**
 * ReactiveReceiver contains RxJava Observables reacting to the Live set data changes.
 *
 * If a reactive approach doesn't fit your needs, consider implementing [CallbackReceiver] instead.
 *
 * @since 0.1
 * @see CallbackReceiver
 */
class ReactiveReceiver internal constructor(liveSetDataManager: LiveSetDataManager) {

    /**
     * Returns the Live set global tempo as Observable.
     *
     * @return the Live set global tempo as Observable
     * @since 0.1
     */
    val tempo: Observable<Float> = liveSetDataManager.tempo

    /**
     * Returns the last changing track device parameter as Observable.
     *
     * @return the last changing track device parameter as Observable
     * @since 0.8
     */
    val trackDeviceParameter: Observable<DeviceParameter> = liveSetDataManager.trackDeviceParameter

    /**
     * Returns the last changing device parameter as Observable.
     *
     * @return the last changing device parameter as Observable
     * @since 0.1
     */
    @Deprecated(message = "use trackDeviceParameter", replaceWith = ReplaceWith("trackDeviceParameter"))
    val deviceParameter: Observable<DeviceParameter> = liveSetDataManager.trackDeviceParameter

    /**
     * Returns the last changing track parameter as Observable.
     *
     * @return the last changing track parameter as Observable
     * @since 0.4
     */
    val trackParameter: Observable<TrackParameter> = liveSetDataManager.trackParameter

    /**
     * Returns the last changing return parameter as Observable.
     *
     * @return the last changing return parameter as Observable
     * @since 0.7
     */
    val returnParameter: Observable<ReturnParameter> = liveSetDataManager.returnParameter

    /**
     * Returns the last changing master parameter as Observable.
     *
     * @return the last changing master parameter as Observable
     * @since 0.7
     */
    val masterParameter: Observable<MasterParameter> = liveSetDataManager.masterParameter

}