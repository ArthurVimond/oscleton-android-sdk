package com.oscleton.sdk.internal

import com.illposed.osc.OSCMessage
import com.oscleton.sdk.enums.SDKResult
import com.oscleton.sdk.utils.Logger
import com.oscleton.sdk.utils.NetworkUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Arthur Vimond on 03/08/2018.
 */
internal class MessageManager internal constructor(private val oscManager: OSCManager) {

    // Public properties

    val oscMessage: Observable<OSCMessage> = oscManager.oscMessage

    val onSetComputerIPError: Observable<String>
        get() = _onSetComputerIPError

    // Private properties

    private val _onSetComputerIPError: PublishSubject<String> = PublishSubject.create()
    // RxJava
    private var compositeDisposable = CompositeDisposable()
    private var setComputerIPCompositeDisposable = CompositeDisposable()

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

        // Set peer
        setPeer()

        startSetComputerIPTimer()
        requestCurrentState()

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

    private fun requestCurrentState() {

        sendMessage(LiveAPI.liveVersion)
        sendMessage(LiveAPI.scriptVersion)

        sendMessage(LiveAPI.tempo)

        sendMessage(LiveAPI.masterVolume)
        sendMessage(LiveAPI.masterPanning)

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