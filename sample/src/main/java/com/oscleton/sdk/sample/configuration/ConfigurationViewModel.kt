package com.oscleton.sdk.sample.configuration

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oscleton.sdk.OscletonSDK
import com.oscleton.sdk.rx.rx
import com.oscleton.sdk.utils.Empty
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class ConfigurationViewModel : ViewModel() {

    private val config = OscletonSDK.instance.config

    // Public properties

    val computerIPAddress: MutableLiveData<String> = MutableLiveData("")

    val liveVersion: LiveData<String> = LiveDataReactiveStreams.fromPublisher(
        config.rx().liveVersion.toFlowable(BackpressureStrategy.LATEST)
    )

    val scriptVersion: LiveData<String> = LiveDataReactiveStreams.fromPublisher(
        OscletonSDK.instance.config.rx().scriptVersion.toFlowable(BackpressureStrategy.LATEST)
    )

    val sdkVersion: LiveData<String> = LiveDataReactiveStreams.fromPublisher(
        config.rx().sdkVersion.toFlowable(BackpressureStrategy.LATEST)
    )

    val onConnectionSuccess: Observable<Empty> = config.rx().onConnectionSuccess
    val onConnectionError: Observable<String> = config.rx().onConnectionError

    val onComputerIPDiscoverySuccess: Observable<String> = config.rx().onComputerIPDiscoverySuccess
    val onComputerIPDiscoveryError: Observable<String> = config.rx().onComputerIPDiscoveryError
    val onComputerIPDiscoveryCancel: Observable<Empty> = config.rx().onComputerIPDiscoveryCancel

    val discoveryIPButtonVisibility: LiveData<Int>
        get() = _discoveryIPButtonVisibility

    val stopDiscoveryButtonVisibility: LiveData<Int>
        get() = _stopDiscoveryButtonVisibility

    val ipDiscoveryProgressBarVisibility: LiveData<Int>
        get() = _ipDiscoveryProgressBarVisibility

    // Private properties

    private val _discoveryIPButtonVisibility: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    private val _stopDiscoveryButtonVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    private val _ipDiscoveryProgressBarVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)

    private val compositeDisposable = CompositeDisposable()

    init {
        observeProperties()
    }

    private fun observeProperties() {

        // IP discovery start
        config.rx().onComputerIPDiscoveryStart
            .subscribe {
                _discoveryIPButtonVisibility.postValue(View.GONE)
                _stopDiscoveryButtonVisibility.postValue(View.VISIBLE)
                _ipDiscoveryProgressBarVisibility.postValue(View.VISIBLE)
            }
            .addTo(compositeDisposable)

        // Auto set computer IP after discovery success
        config.rx().onComputerIPDiscoverySuccess
            .subscribe { computerIP ->
                computerIPAddress.postValue(computerIP)
                _ipDiscoveryProgressBarVisibility.postValue(View.GONE)
            }
            .addTo(compositeDisposable)

        // Hide progress after discovery finish
        config.rx().onComputerIPDiscoverySuccess.map { true }
            .mergeWith(config.rx().onComputerIPDiscoveryError.map { true })
            .mergeWith(config.rx().onComputerIPDiscoveryCancel.map { true })
            .subscribe {
                _discoveryIPButtonVisibility.postValue(View.VISIBLE)
                _stopDiscoveryButtonVisibility.postValue(View.GONE)
                _ipDiscoveryProgressBarVisibility.postValue(View.GONE)
            }
            .addTo(compositeDisposable)

    }

    fun setComputerIPAddress() {
        val computerIP = computerIPAddress.value
        computerIP?.let {
            if (!it.isEmpty()) {
                config.setComputerIP(computerIP)
            }
        }
    }

    fun discoverComputerIPAddress() {
        config.startComputerIPDiscovery()
    }

    fun stopDiscoverComputerIP() {
        config.cancelComputerIPDiscovery()
    }

    override fun onCleared() {
        super.onCleared()
    }
}