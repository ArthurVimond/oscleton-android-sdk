package com.oscleton.sdk

import com.oscleton.sdk.internal.LiveSetDataManager
import com.oscleton.sdk.listeners.*
import com.oscleton.sdk.listeners.OnTempoChangeListener
import com.oscleton.sdk.listeners.OnTrackParameterChangeListener
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
    private var deviceParameterDisp: Disposable? = null
    private var trackParameterDisp: Disposable? = null
    private var returnParameterDisp: Disposable? = null

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
     * Register a callback to be invoked when device parameter changes.
     *
     * @param listener The callback that will run
     * @since 0.1
     */
    fun set(listener: OnDeviceParameterChangeListener) {
        deviceParameterDisp = liveSetDataManager.deviceParameter
                .subscribe {
                    listener.onDeviceParameterChange(it)
                }

        compositeDisposable.add(deviceParameterDisp!!)
    }

    /**
     * Register a callback to be invoked when track parameter changes.
     *
     * @param listener The callback that will run
     * @since 0.4
     */
    fun set(listener: OnTrackParameterChangeListener) {
        trackParameterDisp = liveSetDataManager.trackParameter
                .subscribe {
                    listener.onTrackParameterChange(it)
                }

        compositeDisposable.add(trackParameterDisp!!)
    }

    /**
     * Register a callback to be invoked when return parameter changes.
     *
     * @param listener The callback that will run
     * @since 0.7
     */
    fun set(listener: OnReturnParameterChangeListener) {
        returnParameterDisp = liveSetDataManager.returnParameter
                .subscribe {
                    listener.onReturnParameterChange(it)
                }

        compositeDisposable.add(returnParameterDisp!!)
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

    /**
     * Remove the current [OnDeviceParameterChangeListener].
     *
     * @since 0.1
     */
    fun removeOnDeviceParameterChangeListener() {
        deviceParameterDisp?.dispose()
    }

    /**
     * Remove the current [OnTrackParameterChangeListener].
     *
     * @since 0.4
     */
    fun removeOnTrackParameterChangeListener() {
        trackParameterDisp?.dispose()
    }

    /**
     * Remove the current [OnReturnParameterChangeListener].
     *
     * @since 0.7
     */
    fun removeOnReturnParameterChangeListener() {
        returnParameterDisp?.dispose()
    }
