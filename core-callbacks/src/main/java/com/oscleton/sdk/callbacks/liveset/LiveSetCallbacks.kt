package com.oscleton.sdk.callbacks.liveset

import com.oscleton.sdk.liveset.LiveSetDataManager
import com.oscleton.sdk.callbacks.liveset.listeners.OnTempoChangeListener
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * LiveSetCallbacks contains callbacks triggered by the Live set changes.
 *
 * If you prefer using a reactive approach, consider using LiveSetRx from the 'core-rxjava2' artifact instead.
 *
 * @since 1.0
 */
class LiveSetCallbacks internal constructor(private val liveSetDataManager: LiveSetDataManager) {

    // Rx
    private val compositeDisposable = CompositeDisposable()
    private var tempoDisp: Disposable? = null

    /**
     * Register a callback to be invoked when the general tempo changes.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
    fun set(listener: OnTempoChangeListener) {
        tempoDisp = liveSetDataManager.tempo
                .subscribe {
                    listener.onTempoChange(it)
                }

        compositeDisposable.add(tempoDisp!!)
    }

    /**
     * Remove the current [OnTempoChangeListener].
     *
     * @since 1.0
     */
    fun removeOnTempoChangeListener() {
        tempoDisp?.dispose()
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