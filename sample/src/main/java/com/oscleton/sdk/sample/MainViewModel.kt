package com.oscleton.sdk.sample

import android.arch.lifecycle.ViewModel
import com.oscleton.sdk.OscletonSDK
import com.oscleton.sdk.sample.utils.Logger
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Arthur Vimond on 05/08/2018.
 */
class MainViewModel : ViewModel() {

    // RxJava
    private val compositeDisposable = CompositeDisposable()

    fun configure() {

        // Set computer IP address running Ableton Live
        OscletonSDK.instance.config.setComputerIP("192.168.4.186")
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}