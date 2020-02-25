package com.oscleton.sdk.internal

import android.content.Context
import com.illposed.osc.OSCMessage
import com.oscleton.sdk.enums.SDKResult
import com.oscleton.sdk.utils.Empty
import com.oscleton.sdk.utils.Logger
import com.oscleton.sdk.utils.NetworkUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Created by Arthur Vimond on 03/08/2018.
 */
internal class MessageManager internal constructor(private val context: Context,
                                                   private val oscManager: OSCManager) {

    // Public properties

    val oscMessage: Observable<OSCMessage> = oscManager.oscMessage

    val onSetComputerIPError: Observable<String>
        get() = _onSetComputerIPError

    val onComputerIPDiscoveryStart: Observable<Empty>
        get() = _onComputerIPDiscoveryStart

    val onComputerIPDiscoveryProgress: Observable<Float>
        get() = _onComputerIPDiscoveryProgress

    val onComputerIPDiscoveryError: Observable<String>
        get() = _onComputerIPDiscoveryError

    // Private properties

    private val _onSetComputerIPError: PublishSubject<String> = PublishSubject.create()

    private val _onComputerIPDiscoveryStart: PublishSubject<Empty> = PublishSubject.create()
    private val _onComputerIPDiscoveryProgress: PublishSubject<Float> = PublishSubject.create()
    private val _onComputerIPDiscoveryError: PublishSubject<String> = PublishSubject.create()

    // RxJava
    private var compositeDisposable = CompositeDisposable()
    private var ipDiscoveryCompositeDisposable = CompositeDisposable()
    private var setComputerIPCompositeDisposable = CompositeDisposable()

    private var ipDiscoveryInProgress = false

    init {
        Logger.d("init", this)
    }

    fun setComputerIP(ip: String, port: Int = 9000): SDKResult {
        val isConnected = NetworkUtils.isConnected(context)
        if (!isConnected) {
            _onSetComputerIPError.onNext("no_network_connection")
            return SDKResult.ERROR
        }

        val result = oscManager.initSender(ip, port)

        if (result != SDKResult.SUCCESS) {
            _onSetComputerIPError.onNext("connection_error")
            return result
        }

        setPeer()

        startSetComputerIPTimer()

        return result
    }

    private fun startSetComputerIPTimer() {

        cancelSetComputerIPTimeout()

        setComputerIPCompositeDisposable = CompositeDisposable()

        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe {
                    _onSetComputerIPError.onNext("timeout")
                }
                .addTo(setComputerIPCompositeDisposable)
    }

    fun cancelSetComputerIPTimeout() {
        setComputerIPCompositeDisposable.clear()
    }

    private fun setPeer() {
        val ipAddress = NetworkUtils.deviceIPAddress()
        val args: List<Any> = listOf(ipAddress, 9001)
        sendMessage(LiveAPI.setPeer, args)
    }

    fun startIPDiscovery(): SDKResult {

        val isConnected = NetworkUtils.isConnected(context)
        if (!isConnected) {
            _onComputerIPDiscoveryError.onNext("no_network_connection")
            return SDKResult.ERROR
        }

        if (ipDiscoveryInProgress) {
            return SDKResult.ERROR
        }

        resetIPDiscovery()

        _onComputerIPDiscoveryStart.onNext(Empty.VOID)
        _onComputerIPDiscoveryProgress.onNext(0f)

        ipDiscoveryCompositeDisposable = CompositeDisposable()

        val deviceIPAddress = NetworkUtils.deviceIPAddress()
        val ipFirstPart = deviceIPAddress.substringBeforeLast(".")
        val ipIndexLimit = 100

        Observable.intervalRange(1, 100, 0,100, TimeUnit.MILLISECONDS)
                .subscribe({ ip4thIndex ->
                    val ip = "$ipFirstPart.$ip4thIndex"
                    val port = 9000
                    oscManager.initSender(ip, port)

                    // Discover IP
                    discoverIP(ip)

                    // Progress
                    val progress = ip4thIndex / ipIndexLimit.toFloat()
                    _onComputerIPDiscoveryProgress.onNext(progress)
                }, {
                    // onError
                    _onComputerIPDiscoveryError.onNext("discovery_error")
                }, {
                    // onComplete
                    resetIPDiscovery()
                    _onComputerIPDiscoveryError.onNext("discovery_timeout")
                })
                .addTo(ipDiscoveryCompositeDisposable)

        ipDiscoveryInProgress = true

        return SDKResult.SUCCESS
    }

    fun cancelIPDiscovery() {
        _onComputerIPDiscoveryError.onNext("discovery_cancelled")
        resetIPDiscovery()
    }

    fun resetIPDiscovery() {
        ipDiscoveryInProgress = false
        ipDiscoveryCompositeDisposable.clear()
    }

    private fun discoverIP(computerIP: String) {
        val mobileIPAddress = NetworkUtils.deviceIPAddress()
        val args: List<Any> = listOf(mobileIPAddress, 9001, computerIP)
        sendMessage(LiveAPI.discoverIP, args)
    }

    fun requestCurrentState() {

        sendMessage(LiveAPI.liveVersion)
        sendMessage(LiveAPI.scriptVersion)

        sendMessage(LiveAPI.tempo)

    }

    fun sendMessage(address: String, args: List<Any>? = null) {
        oscManager.sendMessage(address, args)
    }

    // Receiver
    fun connect() {
        oscManager.connect()
    }

    fun startListening() {
        oscManager.startListening()
    }

    fun stopListening() {
        oscManager.stopListening()
    }

    fun disconnect() {
        oscManager.disconnect()
    }

    fun disposeObservers() {
        oscManager.disposeObservers()
        compositeDisposable.clear()
    }

}