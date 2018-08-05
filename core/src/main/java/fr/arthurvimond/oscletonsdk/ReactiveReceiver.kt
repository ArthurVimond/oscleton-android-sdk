package fr.arthurvimond.oscletonsdk

import fr.arthurvimond.oscletonsdk.internal.LiveSetDataManager
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

    val tempo: Observable<Float> = liveSetDataManager.tempo

}