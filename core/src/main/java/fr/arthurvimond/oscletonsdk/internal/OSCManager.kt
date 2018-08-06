package fr.arthurvimond.oscletonsdk.internal

import com.illposed.osc.OSCMessage
import com.illposed.osc.OSCPortIn
import com.illposed.osc.OSCPortOut
import fr.arthurvimond.oscletonsdk.utils.Logger
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.net.InetAddress
import java.net.SocketException

/**
 * Created by Arthur Vimond on 03/08/2018.
 */
internal class OSCManager {

    val oscMessage: PublishSubject<OSCMessage> = PublishSubject.create()

    private lateinit var receiver: OSCPortIn
    private lateinit var sender: OSCPortOut

    // Misc
    private var isConnected = false

    // RxJava
    private var compositeDisposable = CompositeDisposable()

    init {
        Logger.d("init", this)

    }

    fun initSender(ip: String, port: Int = 9000) {
        Logger.d("initSender", this)

        try {
            sender = OSCPortOut(InetAddress.getByName(ip), port)
        } catch (e: SocketException) {
            e.printStackTrace()
        }

    }

    // Receiver
    fun connect(receiverPort: Int = 9001) {
        Logger.d("connect", this)

        if (!isConnected) {
            try {
                receiver = OSCPortIn(receiverPort)
                isConnected = true
            } catch (e: SocketException) {
                e.printStackTrace()
            }

            receiver.addListener("", { _, message ->

//                var string = "oscMessage - address: ${message.address}"
//                message.arguments.forEachIndexed { index, arg ->
//                    string += " - arg[$index]: $arg"
//                }
//                Logger.d(string, this)

                oscMessage.onNext(message)
            })
        }

    }

    fun startListening() {
        Logger.d("startListening", this)
        receiver.startListening()
    }

    fun stopListening() {
        Logger.d("stopListening", this)
        receiver.stopListening()
    }

    fun disconnect() {
        Logger.d("disconnect", this)
        receiver.close()

        isConnected = false
    }

    // Sender
    fun sendMessage(address: String, args: List<Any>? = null) {
        Logger.d("sendMessage - address: $address - args: $args", this)
        Completable
                .fromCallable {
                    sendOSCMessage(address, args)
                }
                .subscribeOn(Schedulers.io())
                .subscribe()
                .addTo(compositeDisposable)
    }

    private fun sendOSCMessage(address: String, args: List<Any>?) {
        val msg = OSCMessage(address, args)
        try {
            sender.send(msg)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    internal fun disposeObservers() {
        compositeDisposable.clear()
    }

}