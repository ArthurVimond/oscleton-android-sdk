package fr.arthurvimond.oscletonsdk

import fr.arthurvimond.oscletonsdk.internal.LiveSetDataManager
import fr.arthurvimond.oscletonsdk.models.DeviceParameter
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
     * Returns the last changing device parameter as Observable.
     *
     * @return the last changing device parameter as Observable
     * @since 0.1
     */
    val deviceParameter: Observable<DeviceParameter> = liveSetDataManager.deviceParameter

}