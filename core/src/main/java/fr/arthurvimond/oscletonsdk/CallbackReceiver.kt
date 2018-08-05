package fr.arthurvimond.oscletonsdk

import fr.arthurvimond.oscletonsdk.internal.LiveSetDataManager
import fr.arthurvimond.oscletonsdk.listeners.OnTempoChangeListener
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * CallbackReceiver contains callbacks triggered by the Live set data changes.
 *
 * If you prefer using a reactive approach, consider implementing [ReactiveReceiver] instead.
 *
 * @since 0.1
 * @see ReactiveReceiver
 */
class CallbackReceiver internal constructor(private val liveSetDataManager: LiveSetDataManager) {

    // RxJava
    private val compositeDisposable = CompositeDisposable()
    private var tempoDisp: Disposable? = null

    /**
     * Register a callback to be invoked when the general tempo changes.
     *
     * @param listener The callback that will run
     * @since 0.1
     */
    fun set(listener: OnTempoChangeListener) {
        tempoDisp = liveSetDataManager.tempo
                .subscribe {
                    listener.onTempoChange(it)
                }

        compositeDisposable.add(tempoDisp!!)
    }

    /**
     * Remove all listeners.
     *
     * @since 0.1
     */
    fun removeAllListeners() {
        compositeDisposable.clear()
    }

    /**
     * Remove the current [OnTempoChangeListener].
     *
     * @since 0.1
     */
    fun removeOnTempoChangeListener() {
        tempoDisp?.dispose()
    }

}