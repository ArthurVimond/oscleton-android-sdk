package com.oscleton.sdk.rx

import com.oscleton.sdk.liveset.LiveSetDataManager
import io.reactivex.Observable

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