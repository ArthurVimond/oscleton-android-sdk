package com.oscleton.sdk.rx

import com.oscleton.sdk.liveset.LiveSetDataManager
import io.reactivex.Observable

/**
 * LiveSetRx contains RxJava Observables reacting to the Live set changes.
 *
 * If a reactive approach doesn't fit your needs, consider using LiveSetCallbacks` from the 'core-callbacks' artifact instead.
 *
 * @since 1.0
 */
class LiveSetRx internal constructor(liveSetDataManager: LiveSetDataManager) {

    /**
     * Returns the Live set global tempo as Observable.
     *
     * @return the Live set global tempo as Observable
     * @since 1.0
     */
    val tempo: Observable<Float> = liveSetDataManager.tempo

    /**
     * Indicates if Live can capture MIDI as Observable.
     *
     * @return indicates if Live can capture MIDI as Observable
     * @since 1.0
     */
    val canCaptureMidi: Observable<Boolean> = liveSetDataManager.canCaptureMidi

}