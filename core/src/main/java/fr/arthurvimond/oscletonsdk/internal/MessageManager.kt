package fr.arthurvimond.oscletonsdk.internal

import com.illposed.osc.OSCMessage
import fr.arthurvimond.oscletonsdk.enums.SDKResult
import fr.arthurvimond.oscletonsdk.utils.Logger
import fr.arthurvimond.oscletonsdk.utils.NetworkUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Arthur Vimond on 03/08/2018.
 */
internal class MessageManager internal constructor(private val oscManager: OSCManager) {

    val oscMessage: Observable<OSCMessage> = oscManager.oscMessage

    // RxJava
    private var compositeDisposable = CompositeDisposable()

    init {
        Logger.d("init", this)
    }

    // Sender
    fun initSender(ip: String, port: Int = 9000): SDKResult {
        val result = oscManager.initSender(ip, port)

        if (result != SDKResult.SUCCESS) {
            return result
        }

        // Set peer
        setPeer()

        // Request current state
        requestCurrentState()

        return result
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