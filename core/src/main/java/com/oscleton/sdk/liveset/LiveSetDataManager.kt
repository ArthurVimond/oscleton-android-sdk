package com.oscleton.sdk.liveset

import com.oscleton.sdk.configuration.ConfigurationDataManager
import com.oscleton.sdk.extensions.boolean
import com.oscleton.sdk.extensions.float
import com.oscleton.sdk.extensions.int
import com.oscleton.sdk.internal.CommonDataManager
import com.oscleton.sdk.internal.LiveAPI
import com.oscleton.sdk.internal.MessageManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.BehaviorSubject

class LiveSetDataManager internal constructor(
    private val messageManager: MessageManager,
    private val configurationDataManager: ConfigurationDataManager,
    private val commonDataManager: CommonDataManager
) {

    // Public properties

    val tempo: Observable<Float>
        get() = _tempo

    val canCaptureMidi: Observable<Boolean>
        get() = _canCaptureMidi

    // Private properties

    private val _tempo: BehaviorSubject<Float> = BehaviorSubject.create()
    private val _canCaptureMidi: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    private val compositeDisposable = CompositeDisposable()

    init {
        observeTransportProperties()
    }

    private fun observeTransportProperties() {

        // Tempo
        messageManager.oscMessage
            .filter { it.address == LiveAPI.tempo }
            .map { it.arguments.first().float }
            .subscribe { _tempo.onNext(it) }
            .addTo(compositeDisposable)

        // Can capture MIDI
        messageManager.oscMessage
            .filter { it.address == LiveAPI.canCaptureMidi }
            .withLatestFrom(configurationDataManager.liveVersion)
            .map { (oscMessage, liveVersion) ->
                val majorLiveVersion = liveVersion.split(".")[0].toInt()
                if (majorLiveVersion <= 10) {
                    oscMessage.arguments.first().int.boolean
                } else {
                    oscMessage.arguments.first().boolean
                }

            }
            .subscribe { _canCaptureMidi.onNext(it) }
            .addTo(compositeDisposable)

    }

}