package com.oscleton.sdk.callbacks.tracks

import com.oscleton.sdk.callbacks.tracks.listeners.OnMasterParameterChangeListener
import com.oscleton.sdk.callbacks.tracks.listeners.OnReturnParameterChangeListener
import com.oscleton.sdk.callbacks.tracks.listeners.OnReturnSendChangeListener
import com.oscleton.sdk.callbacks.tracks.listeners.OnTrackParameterChangeListener
import com.oscleton.sdk.callbacks.tracks.listeners.OnTrackSendChangeListener
import com.oscleton.sdk.tracks.TracksDataManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class TracksCallbacks internal constructor(private val tracksDataManager: TracksDataManager) {

    // Rx
    private val compositeDisposable = CompositeDisposable()
    private var trackParameterDisp: Disposable? = null
    private var returnParameterDisp: Disposable? = null
    private var masterParameterDisp: Disposable? = null
    private var trackSendDisp: Disposable? = null
    private var returnSendDisp: Disposable? = null

    /**
     * Register a callback to be invoked when track parameter changes.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
    fun set(listener: OnTrackParameterChangeListener) {
        trackParameterDisp = tracksDataManager.trackParameter
                .subscribe {
                    listener.onTrackParameterChange(it)
                }

        compositeDisposable.add(trackParameterDisp!!)
    }

    /**
     * Remove the current [OnTrackParameterChangeListener].
     *
     * @since 1.0
     */
    fun removeOnTrackParameterChangeListener() {
        trackParameterDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when return parameter changes.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
    fun set(listener: OnReturnParameterChangeListener) {
        returnParameterDisp = tracksDataManager.returnParameter
                .subscribe {
                    listener.onReturnParameterChange(it)
                }

        compositeDisposable.add(returnParameterDisp!!)
    }

    /**
     * Remove the current [OnReturnParameterChangeListener].
     *
     * @since 1.0
     */
    fun removeOnReturnParameterChangeListener() {
        returnParameterDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when master parameter changes.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
    fun set(listener: OnMasterParameterChangeListener) {
        masterParameterDisp = tracksDataManager.masterParameter
                .subscribe {
                    listener.onMasterParameterChange(it)
                }

        compositeDisposable.add(masterParameterDisp!!)
    }

    /**
     * Remove the current [OnMasterParameterChangeListener].
     *
     * @since 1.0
     */
    fun removeOnMasterParameterChangeListener() {
        masterParameterDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when track send changes.
     *
     * @param listener The callback that will run
     * @since 1.0
     */
    fun set(listener: OnTrackSendChangeListener) {
        trackSendDisp = tracksDataManager.trackSend
                .subscribe {
                    listener.onTrackSendChange(it)
                }

        compositeDisposable.add(trackSendDisp!!)
    }

    /**
     * Remove the current [OnTrackSendChangeListener].
     *
     * @since 1.0
     */
    fun removeOnTrackSendChangeListener() {
        trackSendDisp?.dispose()
    }

    /**
     * Register a callback to be invoked when return track send changes.
     *
     * @param listener The callback that will run
     * @since 1.2
     */
    fun set(listener: OnReturnSendChangeListener) {
        returnSendDisp = tracksDataManager.returnSend
            .subscribe {
                listener.onReturnSendChange(it)
            }

        compositeDisposable.add(returnSendDisp!!)
    }

    /**
     * Remove the current [OnReturnSendChangeListener].
     *
     * @since 1.2
     */
    fun removeOnReturnSendChangeListener() {
        returnSendDisp?.dispose()
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