package com.oscleton.sdk.tracks

import com.illposed.osc.OSCMessage
import com.oscleton.sdk.enums.LiveParameter
import com.oscleton.sdk.enums.MasterParameterIndex
import com.oscleton.sdk.enums.ReturnParameterIndex
import com.oscleton.sdk.enums.SendType
import com.oscleton.sdk.enums.TrackParameterIndex
import com.oscleton.sdk.extensions.automationState
import com.oscleton.sdk.extensions.float
import com.oscleton.sdk.extensions.int
import com.oscleton.sdk.extensions.round
import com.oscleton.sdk.extensions.sendState
import com.oscleton.sdk.extensions.string
import com.oscleton.sdk.internal.CommonDataManager
import com.oscleton.sdk.internal.LiveAPI
import com.oscleton.sdk.internal.MessageManager
import com.oscleton.sdk.models.MasterParameter
import com.oscleton.sdk.models.MasterVolume
import com.oscleton.sdk.models.ReturnParameter
import com.oscleton.sdk.models.Send
import com.oscleton.sdk.models.SendIndices
import com.oscleton.sdk.models.TrackParameter
import com.oscleton.sdk.models.TrackParameterIndices
import com.oscleton.sdk.models.TrackVolume
import com.oscleton.sdk.utils.LiveVolumeUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class TracksDataManager internal constructor(
    private val messageManager: MessageManager,
    private val commonDataManager: CommonDataManager
) {

    // Public properties

    val trackParameter: Observable<TrackParameter>
        get() = _trackParameter

    val returnParameter: Observable<ReturnParameter>
        get() = _returnParameter

    val masterParameter: Observable<MasterParameter>
        get() = _masterParameter

    val trackSend: Observable<Send>
        get() = _trackSend

    val returnSend: Observable<Send>
        get() = _returnSend

    // Private properties

    private val _trackParameter: PublishSubject<TrackParameter> = PublishSubject.create()
    private val _returnParameter: PublishSubject<ReturnParameter> = PublishSubject.create()
    private val _masterParameter: PublishSubject<MasterParameter> = PublishSubject.create()

    private val _trackSend: PublishSubject<Send> = PublishSubject.create()
    private val _returnSend: PublishSubject<Send> = PublishSubject.create()

    private val trackParameters: MutableMap<Int, TrackParameter> = mutableMapOf()
    private val returnParameters: MutableMap<Int, ReturnParameter> = mutableMapOf()
    private val masterParameters: MutableMap<Int, MasterParameter> = mutableMapOf()

    private val trackSends: MutableMap<Pair<Int, Int>, Send> = mutableMapOf()
    private val returnSends: MutableMap<Pair<Int, Int>, Send> = mutableMapOf()

    private val currentTrackParameterIndices: PublishSubject<TrackParameterIndices> =
        PublishSubject.create()
    private val currentReturnParameterIndices: PublishSubject<TrackParameterIndices> =
        PublishSubject.create()
    private val currentMasterParameterIndex: PublishSubject<Int> = PublishSubject.create()

    private val currentTrackSendIndices: PublishSubject<SendIndices> = PublishSubject.create()
    private val currentReturnSendIndices: PublishSubject<SendIndices> = PublishSubject.create()

    private val compositeDisposable = CompositeDisposable()

    init {
        observeTrackVolumeProperties()
        observeReturnVolumeProperties()
        observeMasterVolumeProperties()

        observeTrackSendProperties()
        observeReturnSendProperties()
    }

    private fun observeTrackVolumeProperties() {

        messageManager.oscMessage
            .filter { it.address == LiveAPI.trackVolume }
            .filter { commonDataManager.block(LiveParameter.TRACK_VOLUME) }
            .map { mapToTrackVolume(it) }
            .subscribe {

                // Create new track parameter (if needed)
                if (trackParameters[it.trackIndex] == null) {
                    trackParameters[it.trackIndex] = TrackParameter(
                        trackIndex = it.trackIndex,
                        paramIndex = TrackParameterIndex.VOLUME,
                        trackName = it.trackName,
                        min = 0f,
                        max = 1f
                    )
                }

                val updatedTrackParameter = trackParameters[it.trackIndex]!!.copy(
                    trackIndex = it.trackIndex,
                    trackName = it.trackName,
                    value = it.volume,
                    displayValue = it.displayVolume
                )
                trackParameters[it.trackIndex] = updatedTrackParameter

            }
            .addTo(compositeDisposable)

        // Current track volume index
        messageManager.oscMessage
            .filter { it.address == LiveAPI.trackVolume }
            .filter { commonDataManager.block(LiveParameter.TRACK_VOLUME) }
            .map {
                mapToTrackParameterIndices(
                    oscMessage = it,
                    trackParamIndex = TrackParameterIndex.VOLUME
                )
            }
            .subscribe { currentTrackParameterIndices.onNext(it) }
            .addTo(compositeDisposable)

        // Emit full track info from map
        currentTrackParameterIndices
            .subscribe {
                val trackParam = trackParameters[it.trackIndex]!!
                _trackParameter.onNext(trackParam)
            }
            .addTo(compositeDisposable)

    }

    private fun observeReturnVolumeProperties() {

        messageManager.oscMessage
            .filter { it.address == LiveAPI.returnVolume }
            .filter { commonDataManager.block(LiveParameter.TRACK_VOLUME) }
            .map { mapToTrackVolume(it) }
            .subscribe {

                // Create new return parameter (if needed)
                if (returnParameters[it.trackIndex] == null) {
                    returnParameters[it.trackIndex] = ReturnParameter(
                        trackIndex = it.trackIndex,
                        paramIndex = ReturnParameterIndex.VOLUME,
                        trackName = it.trackName,
                        min = 0f,
                        max = 1f
                    )
                }

                val updatedReturnParameter = returnParameters[it.trackIndex]!!.copy(
                    trackIndex = it.trackIndex,
                    trackName = it.trackName,
                    value = it.volume,
                    displayValue = it.displayVolume
                )
                returnParameters[it.trackIndex] = updatedReturnParameter

            }
            .addTo(compositeDisposable)

        // Current return volume index
        messageManager.oscMessage
            .filter { it.address == LiveAPI.returnVolume }
            .filter { commonDataManager.block(LiveParameter.TRACK_VOLUME) }
            .map {
                mapToTrackParameterIndices(
                    oscMessage = it,
                    trackParamIndex = TrackParameterIndex.VOLUME
                )
            }
            .subscribe { currentReturnParameterIndices.onNext(it) }
            .addTo(compositeDisposable)

        // Emit full return info from map
        currentReturnParameterIndices
            .subscribe {
                val returnParam = returnParameters[it.trackIndex]!!
                _returnParameter.onNext(returnParam)
            }
            .addTo(compositeDisposable)

    }

    private fun observeMasterVolumeProperties() {

        messageManager.oscMessage
            .filter { it.address == LiveAPI.masterVolume }
            .filter { commonDataManager.block(LiveParameter.TRACK_VOLUME) }
            .map { mapToMasterVolume(it) }
            .subscribe {

                // Create new master parameter (if needed)
                if (masterParameters[MasterParameterIndex.VOLUME] == null) {
                    masterParameters[MasterParameterIndex.VOLUME] = MasterParameter(
                        paramIndex = MasterParameterIndex.VOLUME,
                        min = 0f,
                        max = 1f
                    )
                }

                val updatedMasterParameter = masterParameters[MasterParameterIndex.VOLUME]!!.copy(
                    value = it.volume,
                    displayValue = it.displayVolume
                )
                masterParameters[MasterParameterIndex.VOLUME] = updatedMasterParameter

            }
            .addTo(compositeDisposable)

        // Current master volume index
        messageManager.oscMessage
            .filter { it.address == LiveAPI.masterVolume }
            .filter { commonDataManager.block(LiveParameter.TRACK_VOLUME) }
            .map { MasterParameterIndex.VOLUME }
            .subscribe { currentMasterParameterIndex.onNext(it) }
            .addTo(compositeDisposable)

        // Emit full master info from map
        currentMasterParameterIndex
            .subscribe {
                val masterParam = masterParameters[it]!!
                _masterParameter.onNext(masterParam)
            }
            .addTo(compositeDisposable)

    }

    private fun observeTrackSendProperties() {

        messageManager.oscMessage
            .filter { it.address == LiveAPI.trackSend }
            .filter { commonDataManager.block(LiveParameter.TRACK_SEND) }
            .map { mapToSend(it) }
            .subscribe {

                // Create new send (if needed)
                val pair = Pair(it.trackIndex, it.sendIndex)
                if (trackSends[pair] == null) {
                    trackSends[pair] = Send(
                        trackIndex = it.trackIndex,
                        sendIndex = it.sendIndex
                    )
                }

                val updatedSend = trackSends[pair]!!.copy(
                    trackIndex = it.trackIndex,
                    trackName = it.trackName,
                    sendIndex = it.sendIndex,
                    sendName = it.sendName,
                    sendType = it.sendType,
                    volume = it.volume,
                    displayVolume = it.displayVolume,
                    sendState = it.sendState,
                    automationState = it.automationState
                )

                trackSends[pair] = updatedSend
            }
            .addTo(compositeDisposable)

        // Current track send index
        messageManager.oscMessage
            .filter { it.address == LiveAPI.trackSend }
            .filter { commonDataManager.block(LiveParameter.TRACK_SEND) }
            .map { mapToSendIndices(oscMessage = it) }
            .subscribe { currentTrackSendIndices.onNext(it) }
            .addTo(compositeDisposable)

        // Emit full track send info from map
        currentTrackSendIndices
            .subscribe {
                val pair = Pair(it.trackIndex, it.sendIndex)
                val trackSend = trackSends.getValue(pair)
                _trackSend.onNext(trackSend)
            }
            .addTo(compositeDisposable)

    }

    private fun observeReturnSendProperties() {

        messageManager.oscMessage
            .filter { it.address == LiveAPI.returnSend }
            .filter { commonDataManager.block(LiveParameter.TRACK_SEND) }
            .map { mapToSend(it) }
            .subscribe {

                // Create new send (if needed)
                val pair = Pair(it.trackIndex, it.sendIndex)
                if (returnSends[pair] == null) {
                    returnSends[pair] = Send(
                        trackIndex = it.trackIndex,
                        sendIndex = it.sendIndex
                    )
                }

                val updatedSend = returnSends[pair]!!.copy(
                    trackIndex = it.trackIndex,
                    trackName = it.trackName,
                    sendIndex = it.sendIndex,
                    sendName = it.sendName,
                    sendType = it.sendType,
                    volume = it.volume,
                    displayVolume = it.displayVolume,
                    sendState = it.sendState,
                    automationState = it.automationState
                )

                returnSends[pair] = updatedSend
            }
            .addTo(compositeDisposable)

        // Current return send index
        messageManager.oscMessage
            .filter { it.address == LiveAPI.returnSend }
            .filter { commonDataManager.block(LiveParameter.TRACK_SEND) }
            .map { mapToSendIndices(oscMessage = it) }
            .subscribe { currentReturnSendIndices.onNext(it) }
            .addTo(compositeDisposable)

        // Emit full return track send info from map
        currentReturnSendIndices
            .subscribe {
                val pair = Pair(it.trackIndex, it.sendIndex)
                val returnSend = returnSends.getValue(pair)
                _returnSend.onNext(returnSend)
            }
            .addTo(compositeDisposable)

    }

    private fun mapToTrackVolume(oscMessage: OSCMessage): TrackVolume {

        val trackIndex = oscMessage.arguments[0].int
        val trackName = oscMessage.arguments[1].string
        val volume = oscMessage.arguments[2].float
        val displayVolume = convertToTrackDisplayVolume(volume)

        return TrackVolume(
            trackIndex = trackIndex,
            trackName = trackName,
            volume = volume,
            displayVolume = displayVolume
        )
    }

    private fun mapToMasterVolume(oscMessage: OSCMessage): MasterVolume {

        val volume = if (oscMessage.arguments.size == 1) {
            oscMessage.arguments[0].float
        } else {
            oscMessage.arguments[2].float
        }
        val displayVolume = convertToTrackDisplayVolume(volume)

        return MasterVolume(
            volume = volume,
            displayVolume = displayVolume
        )
    }

    private fun mapToTrackParameterIndices(
        oscMessage: OSCMessage,
        trackParamIndex: Int
    ): TrackParameterIndices {

        val trackIndex = oscMessage.arguments[0].int

        return TrackParameterIndices(
            trackIndex = trackIndex,
            paramIndex = trackParamIndex
        )
    }

    private fun convertToTrackDisplayVolume(volume: Float): String {

        val volumeDecibels = LiveVolumeUtils.trackVolumeDecibels(volume)

        if (volumeDecibels == -70f) {
            return "-inf dB"
        }

        return "$volumeDecibels dB"
    }

    private fun mapToSend(oscMessage: OSCMessage): Send {

        val trackIndex = oscMessage.arguments[0].int
        val trackName = oscMessage.arguments[1].string
        val sendIndex = oscMessage.arguments[2].int
        val sendName = oscMessage.arguments[3].string
        val volume = oscMessage.arguments[4].float
        val displayVolume = formattedDisplayVolume(oscMessage.arguments[5].string)
        val sendState = oscMessage.arguments[6].int.sendState
        val automationState = oscMessage.arguments[7].int.automationState

        return Send(
            trackIndex = trackIndex,
            trackName = trackName,
            sendIndex = sendIndex,
            sendName = sendName,
            volume = volume,
            displayVolume = displayVolume,
            sendState = sendState,
            automationState = automationState
        )
    }

    private fun formattedDisplayVolume(volume: String): String {

        if (volume == "-inf dB") {
            return volume
        }

        val digitsVolume = volume.removeSuffix(" dB").toFloat()
        val roundedVolume = digitsVolume.round(1)
        return "$roundedVolume dB"
    }

    private fun mapToSendIndices(oscMessage: OSCMessage): SendIndices {

        val trackIndex = oscMessage.arguments[0].int
        val sendIndex = oscMessage.arguments[2].int

        return SendIndices(
            trackIndex = trackIndex,
            sendIndex = sendIndex
        )
    }

}