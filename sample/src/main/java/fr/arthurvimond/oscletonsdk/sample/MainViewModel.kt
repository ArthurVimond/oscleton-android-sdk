package fr.arthurvimond.oscletonsdk.sample

import android.arch.lifecycle.ViewModel
import fr.arthurvimond.oscletonsdk.OscletonSDK
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
        OscletonSDK.instance.config.setComputerIP("192.168.0.14")
    }

    fun observeLiveEvents() {

        // Listen for tempo changes
        OscletonSDK.instance.receiver.rx.tempo
                .subscribe {
                    Logger.d("rxReceiver - tempo.onNext: $it", this)
                }.addTo(compositeDisposable)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}