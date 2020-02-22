package com.oscleton.sdk.sample.configuration

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oscleton.sdk.OscletonSDK
import com.oscleton.sdk.utils.Empty
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class ConfigurationViewModel : ViewModel() {

    // Public properties

    val computerIPAddress: ObservableField<String> = ObservableField("")

    val liveVersion: LiveData<String> = LiveDataReactiveStreams.fromPublisher(
            OscletonSDK.instance.config.liveVersion.toFlowable(BackpressureStrategy.LATEST))

    val scriptVersion: LiveData<String> = LiveDataReactiveStreams.fromPublisher(
            OscletonSDK.instance.config.scriptVersion.toFlowable(BackpressureStrategy.LATEST))

    val sdkVersion: ObservableField<String> = ObservableField(OscletonSDK.instance.config.sdkVersion)

    val onConnectionSuccess: Observable<Empty> = OscletonSDK.instance.config.onConnectionSuccess
    val onConnectionError: Observable<String> = OscletonSDK.instance.config.onConnectionError

    val onComputerIPDiscoverySuccess: Observable<String> = OscletonSDK.instance.config.onComputerIPDiscoverySuccess
    val onComputerIPDiscoveryError: Observable<String> = OscletonSDK.instance.config.onComputerIPDiscoveryError

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

        val config = OscletonSDK.instance.config

        // IP discovery start
        config.onComputerIPDiscoveryStart
                .subscribe {
                    _discoveryIPButtonVisibility.postValue(View.GONE)
                    _stopDiscoveryButtonVisibility.postValue(View.VISIBLE)
                    _ipDiscoveryProgressBarVisibility.postValue(View.VISIBLE)
                }
                .addTo(compositeDisposable)

        // Auto set computer IP after discovery success
        config.onComputerIPDiscoverySuccess
                .subscribe { computerIP ->
                    computerIPAddress.set(computerIP)
                    _ipDiscoveryProgressBarVisibility.postValue(View.GONE)
                }
                .addTo(compositeDisposable)

        // Hide progress after discovery finish
        config.onComputerIPDiscoverySuccess
                .mergeWith(config.onComputerIPDiscoveryError)
                .subscribe {
                    _discoveryIPButtonVisibility.postValue(View.VISIBLE)
                    _stopDiscoveryButtonVisibility.postValue(View.GONE)
                    _ipDiscoveryProgressBarVisibility.postValue(View.GONE)
                }
                .addTo(compositeDisposable)

    }

    fun setComputerIPAddress() {
        val computerIP = computerIPAddress.get()
        computerIP?.let {
            if (!it.isEmpty()) {
                OscletonSDK.instance.config.setComputerIP(computerIP)
            }
        }
    }

    fun discoverComputerIPAddress() {
        OscletonSDK.instance.config.startComputerIPDiscovery()
    }

    fun stopDiscoverComputerIP() {
        OscletonSDK.instance.config.cancelComputerIPDiscovery()
    }

    override fun onCleared() {
        super.onCleared()
    }
}