package com.oscleton.sdk.sample.receiver

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.oscleton.sdk.OscletonSDK
import com.oscleton.sdk.callbacks.cb
import com.oscleton.sdk.callbacks.liveset.listeners.OnTempoChangeListener
import com.oscleton.sdk.rx.rx
import com.oscleton.sdk.sample.utils.Logger
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class ReceiverViewModel : ViewModel() {

    // Private properties

    private val devicesRx = OscletonSDK.instance.devices.rx()
    private val liveSetRx = OscletonSDK.instance.liveSet.rx()
    private val liveSetCb = OscletonSDK.instance.liveSet.cb()

    // RxJava
    private val compositeDisposable = CompositeDisposable()

    // Public properties

    val trackName: LiveData<String> = LiveDataReactiveStreams.fromPublisher<String>(
            devicesRx.trackDeviceParameter
                    .map { it.trackName }
                    .toFlowable(BackpressureStrategy.LATEST))

    val deviceName: LiveData<String> = LiveDataReactiveStreams.fromPublisher<String>(
            devicesRx.trackDeviceParameter
                    .map { it.deviceName }
                    .toFlowable(BackpressureStrategy.LATEST))

    val deviceParameterName: LiveData<String> = LiveDataReactiveStreams.fromPublisher<String>(
            devicesRx.trackDeviceParameter
                    .map { it.paramName }
                    .toFlowable(BackpressureStrategy.LATEST))

    val deviceParameterValue: LiveData<String> = LiveDataReactiveStreams.fromPublisher<String>(
            devicesRx.trackDeviceParameter
                    .map { "${it.value}" }
                    .toFlowable(BackpressureStrategy.LATEST))

    val deviceParameterDisplayValue: LiveData<String> = LiveDataReactiveStreams.fromPublisher<String>(
            devicesRx.trackDeviceParameter
                    .map { it.displayValue }
                    .toFlowable(BackpressureStrategy.LATEST))

    val deviceParameterMin: LiveData<String> = LiveDataReactiveStreams.fromPublisher<String>(
            devicesRx.trackDeviceParameter
                    .map { "${it.min}" }
                    .toFlowable(BackpressureStrategy.LATEST))

    val deviceParameterMax: LiveData<String> = LiveDataReactiveStreams.fromPublisher<String>(
            devicesRx.trackDeviceParameter
                    .map { "${it.max}" }
                    .toFlowable(BackpressureStrategy.LATEST))

    init {
        observeProperties()
    }

    private fun observeProperties() {

        // Listen for tempo changes
        liveSetRx.tempo
                .subscribe {
                    Logger.d("rxReceiver - tempo.onNext: $it", this)
                }.addTo(compositeDisposable)

        // Add tempo listener
        liveSetCb.set(OnTempoChangeListener {
            Logger.d("liveSetCb - tempo.onNext: $it", this)
        })

        // Listen for device parameter changes
        devicesRx.trackDeviceParameter
                .subscribe {
                    Logger.d("trackDeviceParameter.onNext: name: ${it.paramName} - value: ${it.value}" +
                            " - track: ${it.trackIndex} - device: ${it.deviceIndex} - param: ${it.paramIndex}", this)
                }.addTo(compositeDisposable)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}