package com.oscleton.sdk.configuration

import com.oscleton.sdk.extensions.string
import com.oscleton.sdk.internal.LiveAPI
import com.oscleton.sdk.internal.MessageManager
import com.oscleton.sdk.utils.Empty
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class ConfigurationDataManager internal constructor(private val messageManager: MessageManager) {

    // Public properties

    val onStart: Observable<Empty>
        get() = _onStart

    val onQuit: Observable<Empty>
        get() = _onQuit

    val liveVersion: Observable<String>
        get() = _liveVersion

    val scriptVersion: Observable<String>
        get() = _scriptVersion

    val onSetPeerSuccess: Observable<Empty>
        get() = _onSetPeerSuccess

    val onComputerIPDiscoverySuccess: Observable<String>
        get() = _onComputerIPDiscoverySuccess

    val onSetComputerIPError: Observable<String> = messageManager.onSetComputerIPError

    val onComputerIPDiscoveryStart: Observable<Empty> = messageManager.onComputerIPDiscoveryStart

    val onComputerIPDiscoveryProgress: Observable<Float> = messageManager.onComputerIPDiscoveryProgress

    val onComputerIPDiscoveryError: Observable<String> = messageManager.onComputerIPDiscoveryError

    val onComputerIPDiscoveryCancel: Observable<Empty> = messageManager.onComputerIPDiscoveryCancel

    // Private properties

    private val _onStart: PublishSubject<Empty> = PublishSubject.create()
    private val _onQuit: PublishSubject<Empty> = PublishSubject.create()
    private val _liveVersion: BehaviorSubject<String> = BehaviorSubject.create()
    private val _scriptVersion: BehaviorSubject<String> = BehaviorSubject.create()
    private val _onSetPeerSuccess: PublishSubject<Empty> = PublishSubject.create()
    private val _onComputerIPDiscoverySuccess: PublishSubject<String> = PublishSubject.create()

    private val compositeDisposable = CompositeDisposable()

    init {
        observeConfigProperties()
    }

    private fun observeConfigProperties() {

        // Start
        messageManager.oscMessage
                .filter { it.address == LiveAPI.start }
                .map { Empty.VOID }
                .subscribe { _onStart.onNext(it) }
                .addTo(compositeDisposable)

        // Quit
        messageManager.oscMessage
                .filter { it.address == LiveAPI.quit }
                .map { Empty.VOID }
                .subscribe { _onQuit.onNext(it) }
                .addTo(compositeDisposable)

        // Live version
        messageManager.oscMessage
                .filter { it.address == LiveAPI.liveVersion }
                .map { it.arguments.first().string }
                .subscribe { _liveVersion.onNext(it) }
                .addTo(compositeDisposable)

        // Script version
        messageManager.oscMessage
                .filter { it.address == LiveAPI.scriptVersion }
                .map { it.arguments.first().string }
                .subscribe { _scriptVersion.onNext(it) }
                .addTo(compositeDisposable)

        // SetPeer success
        messageManager.oscMessage
                .filter { it.address == LiveAPI.setPeerSuccess }
                .map { it.arguments.first() }
                .subscribe { _onSetPeerSuccess.onNext(Empty.VOID) }
                .addTo(compositeDisposable)

        _onSetPeerSuccess
                .subscribe {
                    messageManager.cancelSetComputerIPTimeout()
                }
                .addTo(compositeDisposable)

        // DiscoverIP success
        messageManager.oscMessage
                .filter { it.address == LiveAPI.discoverIPSuccess }
                .map { it.arguments.first().string }
                .debounce(250, TimeUnit.MILLISECONDS) // needed to avoid duplicates on multi-link sometimes
                .subscribe { _onComputerIPDiscoverySuccess.onNext(it) }
                .addTo(compositeDisposable)

    }

}