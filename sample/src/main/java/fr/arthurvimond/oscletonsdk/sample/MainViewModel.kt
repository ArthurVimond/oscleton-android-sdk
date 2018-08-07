package fr.arthurvimond.oscletonsdk.sample

import android.arch.lifecycle.ViewModel
import fr.arthurvimond.oscletonsdk.OscletonSDK
import io.reactivex.disposables.CompositeDisposable

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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}