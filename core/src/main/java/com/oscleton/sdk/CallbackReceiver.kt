package com.oscleton.sdk

import com.oscleton.sdk.devices.DevicesDataManager
import com.oscleton.sdk.listeners.*
import com.oscleton.sdk.liveset.LiveSetDataManager
import com.oscleton.sdk.tracks.TracksDataManager
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
@Deprecated(message = "Use corresponding cb() extension functions from the 'core-callbacks' artifact instead")
class CallbackReceiver internal constructor(private val liveSetDataManager: LiveSetDataManager,
                                            private val tracksDataManager: TracksDataManager,
                                            private val devicesDataManager: DevicesDataManager) {

    // RxJava
    private val compositeDisposable = CompositeDisposable()
    private var tempoDisp: Disposable? = null
    private var trackSendDisp: Disposable? = null
    private var trackDeviceParameterDisp: Disposable? = null
    private var trackParameterDisp: Disposable? = null
    private var returnDeviceParameterDisp: Disposable? = null
    private var returnParameterDisp: Disposable? = null
    private var masterParameterDisp: Disposable? = null
    private var masterDeviceParameterDisp: Disposable? = null

    /**
     * Register a callback to be invoked when the general tempo changes.
     *
     * @param listener The callback that will run
     * @since 0.1
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.liveSet.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.liveSet.cb().set(listener)"))
    fun set(listener: OnTempoChangeListener) {
        tempoDisp = liveSetDataManager.tempo
                .subscribe {
                    listener.onTempoChange(it)
                }

        compositeDisposable.add(tempoDisp!!)
    }

    /**
     * Register a callback to be invoked when track device parameter changes.
     *
     * @param listener The callback that will run
     * @since 0.8
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.devices.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.devices.cb().set(listener)"))
    fun set(listener: OnTrackDeviceParameterChangeListener) {
        trackDeviceParameterDisp = devicesDataManager.trackDeviceParameter
                .subscribe {
                    listener.onTrackDeviceParameterChange(it)
                }

        compositeDisposable.add(trackDeviceParameterDisp!!)
    }

    /**
     * Register a callback to be invoked when track parameter changes.
     *
     * @param listener The callback that will run
     * @since 0.4
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.tracks.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.tracks.cb().set(listener)"))
    fun set(listener: OnTrackParameterChangeListener) {
        trackParameterDisp = tracksDataManager.trackParameter
                .subscribe {
                    listener.onTrackParameterChange(it)
                }

        compositeDisposable.add(trackParameterDisp!!)
    }

    /**
     * Register a callback to be invoked when return device parameter changes.
     *
     * @param listener The callback that will run
     * @since 0.9
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.devices.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.devices.cb().set(listener)"))
    fun set(listener: OnReturnDeviceParameterChangeListener) {
        returnDeviceParameterDisp = devicesDataManager.returnDeviceParameter
                .subscribe {
                    listener.onReturnDeviceParameterChange(it)
                }

        compositeDisposable.add(returnDeviceParameterDisp!!)
    }

    /**
     * Register a callback to be invoked when return parameter changes.
     *
     * @param listener The callback that will run
     * @since 0.7
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.tracks.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.tracks.cb().set(listener)"))
    fun set(listener: OnReturnParameterChangeListener) {
        returnParameterDisp = tracksDataManager.returnParameter
                .subscribe {
                    listener.onReturnParameterChange(it)
                }

        compositeDisposable.add(returnParameterDisp!!)
    }

    /**
     * Register a callback to be invoked when master parameter changes.
     *
     * @param listener The callback that will run
     * @since 0.7
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.tracks.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.tracks.cb().set(listener)"))
    fun set(listener: OnMasterParameterChangeListener) {
        masterParameterDisp = tracksDataManager.masterParameter
                .subscribe {
                    listener.onMasterParameterChange(it)
                }

        compositeDisposable.add(masterParameterDisp!!)
    }

    /**
     * Register a callback to be invoked when master device parameter changes.
     *
     * @param listener The callback that will run
     * @since 0.9
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.devices.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.devices.cb().set(listener)"))
    fun set(listener: OnMasterDeviceParameterChangeListener) {
        masterDeviceParameterDisp = devicesDataManager.masterDeviceParameter
                .subscribe {
                    listener.onMasterDeviceParameterChange(it)
                }

        compositeDisposable.add(returnDeviceParameterDisp!!)
    }

    /**
     * Register a callback to be invoked when track send changes.
     *
     * @param listener The callback that will run
     * @since 0.4
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.tracks.cb().set(listener) instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.tracks.cb().set(listener)"))
    fun set(listener: OnTrackSendChangeListener) {
        trackSendDisp = tracksDataManager.trackSend
                .subscribe {
                    listener.onTrackSendChange(it)
                }

        compositeDisposable.add(trackSendDisp!!)
    }

    /**
     * Remove all listeners.
     *
     * @since 0.1
     */
    @Deprecated(
            message = "Use removeAllListeners() in corresponding cb() extension functions from the 'core-callbacks' artifacts instead")
    fun removeAllListeners() {
        compositeDisposable.clear()
    }

    /**
     * Remove the current [OnTempoChangeListener].
     *
     * @since 0.1
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.liveSet.cb().removeOnTempoChangeListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.liveSet.cb().removeOnTempoChangeListener()"))
    fun removeOnTempoChangeListener() {
        tempoDisp?.dispose()
    }

    /**
     * Remove the current [OnTrackParameterChangeListener].
     *
     * @since 0.4
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.tracks.cb().removeOnTrackParameterChangeListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.tracks.cb().removeOnTrackParameterChangeListener()"))
    fun removeOnTrackParameterChangeListener() {
        trackParameterDisp?.dispose()
    }

    /**
     * Remove the current [OnTrackSendChangeListener].
     *
     * @since 0.8
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.tracks.cb().removeOnTrackSendChangeListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.tracks.cb().removeOnTrackSendChangeListener()"))
    fun removeOnTrackSendChangeListener() {
        trackSendDisp?.dispose()
    }

    /**
     * Remove the current [OnTrackDeviceParameterChangeListener].
     *
     * @since 0.8
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.devices.cb().removeOnTrackDeviceParameterChangeListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.devices.cb().removeOnTrackDeviceParameterChangeListener()"))
    fun removeOnTrackDeviceParameterChangeListener() {
        trackDeviceParameterDisp?.dispose()
    }

    /**
     * Remove the current [OnReturnDeviceParameterChangeListener].
     *
     * @since 0.9
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.devices.cb().removeOnReturnDeviceParameterChangeListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.devices.cb().removeOnReturnDeviceParameterChangeListener()"))
    fun removeOnReturnDeviceParameterChangeListener() {
        returnDeviceParameterDisp?.dispose()
    }

    /**
     * Remove the current [OnReturnParameterChangeListener].
     *
     * @since 0.7
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.tracks.cb().removeOnReturnParameterChangeListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.tracks.cb().removeOnReturnParameterChangeListener()"))
    fun removeOnReturnParameterChangeListener() {
        returnParameterDisp?.dispose()
    }

    /**
     * Remove the current [OnMasterParameterChangeListener].
     *
     * @since 0.7
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.tracks.cb().removeOnMasterParameterChangeListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.tracks.cb().removeOnMasterParameterChangeListener()"))
    fun removeOnMasterParameterChangeListener() {
        masterParameterDisp?.dispose()
    }

    /**
     * Remove the current [OnMasterDeviceParameterChangeListener].
     *
     * @since 0.9
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.devices.cb().removeOnMasterDeviceParameterChangeListener() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.devices.cb().removeOnMasterDeviceParameterChangeListener()"))
    fun removeOnMasterDeviceParameterChangeListener() {
        masterDeviceParameterDisp?.dispose()
    }

}