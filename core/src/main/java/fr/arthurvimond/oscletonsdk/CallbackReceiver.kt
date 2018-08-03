package fr.arthurvimond.oscletonsdk

import fr.arthurvimond.oscletonsdk.internal.LiveSetDataManager
import io.reactivex.disposables.CompositeDisposable

/**
 * CallbackReceiver contains callbacks triggered by the Live set data changes.
 *
 * If you prefer using a reactive approach, consider implementing [ReactiveReceiver] instead.
 *
 * @since 0.1
 * @see ReactiveReceiver
 */
class CallbackReceiver internal constructor(private val liveSetDataManager: LiveSetDataManager) {

    // Rx
    private val compositeDisposable = CompositeDisposable()

    init {
        observeProperties()
    }

    private fun observeProperties() {



    }

}