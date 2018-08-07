package fr.arthurvimond.oscletonsdk.sample.receiver

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModel
import fr.arthurvimond.oscletonsdk.OscletonSDK
import fr.arthurvimond.oscletonsdk.listeners.OnTempoChangeListener
import fr.arthurvimond.oscletonsdk.sample.utils.Logger
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class ReceiverViewModel : ViewModel() {

    // Private properties

    private val reactiveReceiver = OscletonSDK.instance.receiver.rx

    // RxJava
    private val compositeDisposable = CompositeDisposable()

    // Public properties

    val deviceParameterName: LiveData<String> = LiveDataReactiveStreams.fromPublisher<String>(
            reactiveReceiver.deviceParameter
                    .map { it.name }
                    .toFlowable(BackpressureStrategy.LATEST))

    val deviceParameterValue: LiveData<String> = LiveDataReactiveStreams.fromPublisher<String>(
            reactiveReceiver.deviceParameter
                    .map { "${it.value}" }
                    .toFlowable(BackpressureStrategy.LATEST))

    init {
        observeProperties()
    }

    private fun observeProperties() {

        val reactiveReceiver = OscletonSDK.instance.receiver.rx
        val callbackReceiver = OscletonSDK.instance.receiver.cb

        // Listen for tempo changes
        reactiveReceiver.tempo
                .subscribe {
                    Logger.d("rxReceiver - tempo.onNext: $it", this)
                }.addTo(compositeDisposable)

        // Add tempo listener
        callbackReceiver.set(OnTempoChangeListener {
            Logger.d("cbReceiver - tempo.onNext: $it", this)
        })

        // Listen for device parameter changes
        reactiveReceiver.deviceParameter
                .subscribe {
                    Logger.d("deviceParameter.onNext: name: ${it.name} - value: ${it.value}" +
                            " - track: ${it.trackIndex} - device: ${it.deviceIndex} - param: ${it.paramIndex}", this)
                }.addTo(compositeDisposable)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}