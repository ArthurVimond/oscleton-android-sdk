package com.oscleton.sdk.rx

import com.oscleton.sdk.models.MasterParameter
import com.oscleton.sdk.models.ReturnParameter
import com.oscleton.sdk.models.Send
import com.oscleton.sdk.models.TrackParameter
import com.oscleton.sdk.tracks.TracksDataManager
import io.reactivex.Observable

/**
 * TracksRx contains RxJava Observables reacting to the Live tracks changes.
 *
 * If a reactive approach doesn't fit your needs, consider using `TracksCallbacks` from the 'core-callbacks' artifact instead.
 *
 * @since 1.0
 */
class TracksRx internal constructor(tracksDataManager: TracksDataManager) {

    /**
     * Returns the last changing track parameter as Observable.
     *
     * @return the last changing track parameter as Observable
     * @since 1.0
     */
    val trackParameter: Observable<TrackParameter> = tracksDataManager.trackParameter

    /**
     * Returns the last changing track send as Observable.
     *
     * @return the last changing track send as Observable
     * @since 1.0
     */
    val trackSend: Observable<Send> = tracksDataManager.trackSend

    /**
     * Returns the last changing return parameter as Observable.
     *
     * @return the last changing return parameter as Observable
     * @since 1.0
     */
    val returnParameter: Observable<ReturnParameter> = tracksDataManager.returnParameter

    /**
     * Returns the last changing master parameter as Observable.
     *
     * @return the last changing master parameter as Observable
     * @since 1.0
     */
    val masterParameter: Observable<MasterParameter> = tracksDataManager.masterParameter

}