package fr.arthurvimond.oscletonsdk.internal

import fr.arthurvimond.oscletonsdk.utils.Logger
import fr.arthurvimond.oscletonsdk.utils.NetworkUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

/**
 * Created by Arthur Vimond on 03/08/2018.
 */
internal class MessageManager internal constructor(private val oscManager: OSCManager,
                                                   private val messageParser: MessageParser,
                                                   private val liveSetDataManager: LiveSetDataManager) {

    // RxJava
    private var compositeDisposable = CompositeDisposable()

    init {
        Logger.d("init", this)
    }

    // Sender
    fun initSender(ip: String, port: Int = 9000) {
        oscManager.initSender(ip, port)

        // Set peer
        setPeer()

        // Request current state
        requestCurrentState()
    }

    private fun setPeer() {
        val ipAddress = NetworkUtils.deviceIPAddress()
        val args: List<Any> = listOf(ipAddress, 9001)
        sendMessage(LiveAPI.setPeer, args)
    }

    private fun requestCurrentState() {

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

    fun observeOSCProperties() {

        oscManager.oscMessage
                .subscribe {
                    messageParser.parse(it.address, it.arguments)
                }
                .addTo(compositeDisposable)

        liveSetDataManager.neededOSCMessage
                .subscribe {
                    sendMessage(it.address, it.arguments)
                }
                .addTo(compositeDisposable)

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