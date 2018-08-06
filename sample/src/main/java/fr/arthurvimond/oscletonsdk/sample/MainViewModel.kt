package fr.arthurvimond.oscletonsdk.sample

import android.arch.lifecycle.ViewModel
import fr.arthurvimond.oscletonsdk.OscletonSDK
import fr.arthurvimond.oscletonsdk.listeners.OnDeviceParameterChangeListener
import fr.arthurvimond.oscletonsdk.listeners.OnTempoChangeListener
import fr.arthurvimond.oscletonsdk.sample.utils.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

/**
 * Created by Arthur Vimond on 05/08/2018.
 */
class MainViewModel : ViewModel() {

    // RxJava
    private val compositeDisposable = CompositeDisposable()

    fun configure() {

        // Set computer IP address running Ableton Live
        OscletonSDK.instance.config.setComputerIP("192.168.4.239")
    }

    fun observeLiveEvents() {

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

        // Add device parameter listener
        callbackReceiver.set(OnDeviceParameterChangeListener {
            Logger.d("cbReceiver - deviceParameter.onNext: $it", this)
        })

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}