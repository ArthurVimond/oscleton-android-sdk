package com.oscleton.sdk.internal

import com.illposed.osc.OSCMessage
import com.oscleton.sdk.enums.*
import com.oscleton.sdk.extensions.*
import com.oscleton.sdk.models.*
import com.oscleton.sdk.utils.Empty
import com.oscleton.sdk.utils.LiveVolumeUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

internal class LiveSetDataManager internal constructor(private val messageManager: MessageManager) {

    // Public properties

    // Config

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

    // Transport

    val tempo: Observable<Float>
        get() = _tempo


    // Device parameters

    val trackDeviceParameter: Observable<DeviceParameter>
        get() = _trackDeviceParameter

    val trackParameter: Observable<TrackParameter>
        get() = _trackParameter

    val trackSend: Observable<Send>
        get() = _trackSend

    val returnDeviceParameter: Observable<DeviceParameter>
        get() = _returnDeviceParameter

    val returnParameter: Observable<ReturnParameter>
        get() = _returnParameter

    val masterParameter: Observable<MasterParameter>
        get() = _masterParameter

    fun disableParameters(parameters: List<String>) {
        disabledParameters.clear()
        disabledParameters.addAll(parameters)
    }

    fun enableAllParameters() {
        disabledParameters.clear()
    }

    // Private properties

    private val _onStart: PublishSubject<Empty> = PublishSubject.create()
    private val _onQuit: PublishSubject<Empty> = PublishSubject.create()
    private val _liveVersion: BehaviorSubject<String> = BehaviorSubject.create()
    private val _scriptVersion: BehaviorSubject<String> = BehaviorSubject.create()
    private val _onSetPeerSuccess: PublishSubject<Empty> = PublishSubject.create()
    private val _onComputerIPDiscoverySuccess: PublishSubject<String> = PublishSubject.create()

    private val _tempo: BehaviorSubject<Float> = BehaviorSubject.create()

    private val _trackDeviceParameter: PublishSubject<DeviceParameter> = PublishSubject.create()
    private val _trackParameter: PublishSubject<TrackParameter> = PublishSubject.create()
    private val _trackSend: PublishSubject<Send> = PublishSubject.create()
    private val _returnDeviceParameter: PublishSubject<DeviceParameter> = PublishSubject.create()
    private val _returnParameter: PublishSubject<ReturnParameter> = PublishSubject.create()
    private val _masterParameter: PublishSubject<MasterParameter> = PublishSubject.create()

    // RxJava
    private val compositeDisposable = CompositeDisposable()

    private val trackParameters: MutableMap<Int, TrackParameter> = mutableMapOf()
    private val trackDevices: MutableMap<Pair<Int, Int>, Device> = mutableMapOf()
    private val trackDeviceParameters: MutableMap<Triple<Int, Int, Int>, DeviceParameter> = mutableMapOf()
    private val trackSends: MutableMap<Pair<Int, Int>, Send> = mutableMapOf()
    private val returnDevices: MutableMap<Pair<Int, Int>, Device> = mutableMapOf()
    private val returnDeviceParameters: MutableMap<Triple<Int, Int, Int>, DeviceParameter> = mutableMapOf()
    private val returnParameters: MutableMap<Int, ReturnParameter> = mutableMapOf()
    private val masterParameters: MutableMap<Int, MasterParameter> = mutableMapOf()

    private val currentTrackDeviceParameterIndices: PublishSubject<DeviceParameterIndices> = PublishSubject.create()
    private val currentReturnDeviceParameterIndices: PublishSubject<DeviceParameterIndices> = PublishSubject.create()
    private val currentTrackParameterIndices: PublishSubject<TrackParameterIndices> = PublishSubject.create()
    private val currentTrackSendIndices: PublishSubject<SendIndices> = PublishSubject.create()
    private val currentReturnParameterIndices: PublishSubject<TrackParameterIndices> = PublishSubject.create()
    private val currentMasterParameterIndex: PublishSubject<Int> = PublishSubject.create()

    private val disabledParameters: MutableSet<String> = mutableSetOf()

    init {
        observeConfigProperties()
        observeTransportProperties()

        observeTrackVolumeProperties()
        observeTrackSendProperties()
        observeTrackDeviceParametersProperties()

        observeReturnVolumeProperties()
        observeReturnDeviceParametersProperties()

        observeMasterVolumeProperties()
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

    private fun observeTransportProperties() {

        // Tempo
        messageManager.oscMessage
                .filter { it.address == LiveAPI.tempo }
                .map { it.arguments.first().float }
                .subscribe { _tempo.onNext(it) }
                .addTo(compositeDisposable)

    }

    private fun observeTrackDeviceParametersProperties() {

        // Fill the maps on track device parameters changes
        messageManager.oscMessage
                .filter { it.address == LiveAPI.trackDeviceParam }
                .filter { block(LiveParameter.DEVICE_PARAMETER) }
                .map { mapToDeviceParameter(it) }
                .subscribe {

                    // Create new device (if needed)
                    val pair = Pair(it.trackIndex, it.deviceIndex)
                    if (trackDevices[pair] == null) {
                        trackDevices[pair] = Device(it.trackIndex, it.deviceIndex)
                    }

                    trackDevices[pair]!!.trackIndex = it.trackIndex
                    trackDevices[pair]!!.deviceIndex = it.deviceIndex

                    // Track device parameter
                    val triple = Triple(it.trackIndex, it.deviceIndex, it.paramIndex)
                    trackDeviceParameters[triple] = it

                }.addTo(compositeDisposable)

        // Current track device parameter indices
        messageManager.oscMessage
                .filter { it.address == LiveAPI.trackDeviceParam }
                .filter { block(LiveParameter.DEVICE_PARAMETER) }
                .map { mapToDeviceParameterIndices(it) }
                .subscribe { currentTrackDeviceParameterIndices.onNext(it) }
                .addTo(compositeDisposable)

        // Emit full track device parameter info from map
        currentTrackDeviceParameterIndices
                .subscribe {
                    val triple = Triple(it.trackIndex, it.deviceIndex, it.paramIndex)
                    val deviceParam = trackDeviceParameters.getValue(triple)
                    _trackDeviceParameter.onNext(deviceParam)
                }
                .addTo(compositeDisposable)

    }

    private fun observeTrackVolumeProperties() {

        messageManager.oscMessage
                .filter { it.address == LiveAPI.trackVolume }
                .filter { block(LiveParameter.TRACK_VOLUME) }
                .map { mapToTrackVolume(it) }
                .subscribe {

                    // Create new track parameter (if needed)
                    if (trackParameters[it.trackIndex] == null) {
                        trackParameters[it.trackIndex] = TrackParameter(
                                trackIndex = it.trackIndex,
                                paramIndex = TrackParameterIndex.VOLUME,
                                trackName = it.trackName,
                                min = 0f,
                                max = 1f)
                    }

                    trackParameters[it.trackIndex]!!.trackIndex = it.trackIndex
                    trackParameters[it.trackIndex]!!.trackName = it.trackName
                    trackParameters[it.trackIndex]!!.value = it.volume
                    trackParameters[it.trackIndex]!!.displayValue = it.displayVolume

                }
                .addTo(compositeDisposable)

        // Current track volume index
        messageManager.oscMessage
                .filter { it.address == LiveAPI.trackVolume }
                .filter { block(LiveParameter.TRACK_VOLUME) }
                .map { mapToTrackParameterIndices(oscMessage = it, trackParamIndex = TrackParameterIndex.VOLUME) }
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

    private fun observeTrackSendProperties() {

        messageManager.oscMessage
                .filter { it.address == LiveAPI.trackSend }
                .filter { block(LiveParameter.TRACK_SEND) }
                .map { mapToSend(it) }
                .subscribe {

                    // Create new send (if needed)
                    val pair = Pair(it.trackIndex, it.sendIndex)
                    if (trackSends[pair] == null) {
                        trackSends[pair] = Send(
                                trackIndex = it.trackIndex,
                                sendIndex = it.sendIndex)
                    }

                    trackSends[pair]!!.trackIndex = it.trackIndex
                    trackSends[pair]!!.trackName = it.trackName
                    trackSends[pair]!!.sendIndex = it.sendIndex
                    trackSends[pair]!!.sendName = it.sendName
                    trackSends[pair]!!.sendType = SendType.TRACK
                    trackSends[pair]!!.volume = it.volume
                    trackSends[pair]!!.displayVolume = it.displayVolume
                    trackSends[pair]!!.sendState = it.sendState
                    trackSends[pair]!!.automationState = it.automationState
                }
                .addTo(compositeDisposable)

        // Current track send index
        messageManager.oscMessage
                .filter { it.address == LiveAPI.trackSend }
                .filter { block(LiveParameter.TRACK_SEND) }
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

    private fun observeReturnVolumeProperties() {

        messageManager.oscMessage
                .filter { it.address == LiveAPI.returnVolume }
                .filter { block(LiveParameter.TRACK_VOLUME) }
                .map { mapToTrackVolume(it) }
                .subscribe {

                    // Create new return parameter (if needed)
                    if (returnParameters[it.trackIndex] == null) {
                        returnParameters[it.trackIndex] = ReturnParameter(
                                trackIndex = it.trackIndex,
                                paramIndex = ReturnParameterIndex.VOLUME,
                                trackName = it.trackName,
                                min = 0f,
                                max = 1f)
                    }

                    returnParameters[it.trackIndex]!!.trackIndex = it.trackIndex
                    returnParameters[it.trackIndex]!!.trackName = it.trackName
                    returnParameters[it.trackIndex]!!.value = it.volume
                    returnParameters[it.trackIndex]!!.displayValue = it.displayVolume

                }
                .addTo(compositeDisposable)

        // Current return volume index
        messageManager.oscMessage
                .filter { it.address == LiveAPI.returnVolume }
                .filter { block(LiveParameter.TRACK_VOLUME) }
                .map { mapToTrackParameterIndices(oscMessage = it, trackParamIndex = TrackParameterIndex.VOLUME) }
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

    private fun observeReturnDeviceParametersProperties() {

        // Fill the maps on return device parameters changes
        messageManager.oscMessage
                .filter { it.address == LiveAPI.returnDeviceParam }
                .filter { block(LiveParameter.DEVICE_PARAMETER) }
                .map { mapToDeviceParameter(it) }
                .subscribe {

                    // Create new device (if needed)
                    val pair = Pair(it.trackIndex, it.deviceIndex)
                    if (returnDevices[pair] == null) {
                        returnDevices[pair] = Device(it.trackIndex, it.deviceIndex)
                    }

                    returnDevices[pair]!!.trackIndex = it.trackIndex
                    returnDevices[pair]!!.deviceIndex = it.deviceIndex

                    // Return device parameter
                    val triple = Triple(it.trackIndex, it.deviceIndex, it.paramIndex)
                    returnDeviceParameters[triple] = it

                }.addTo(compositeDisposable)

        // Current return device parameter indices
        messageManager.oscMessage
                .filter { it.address == LiveAPI.returnDeviceParam }
                .filter { block(LiveParameter.DEVICE_PARAMETER) }
                .map { mapToDeviceParameterIndices(it) }
                .subscribe { currentReturnDeviceParameterIndices.onNext(it) }
                .addTo(compositeDisposable)

        // Emit full return device parameter info from map
        currentReturnDeviceParameterIndices
                .subscribe {
                    val triple = Triple(it.trackIndex, it.deviceIndex, it.paramIndex)
                    val deviceParam = returnDeviceParameters.getValue(triple)
                    _returnDeviceParameter.onNext(deviceParam)
                }
                .addTo(compositeDisposable)
    }

    private fun observeMasterVolumeProperties() {

        messageManager.oscMessage
                .filter { it.address == LiveAPI.masterVolume }
                .filter { block(LiveParameter.TRACK_VOLUME) }
                .map { mapToMasterVolume(it) }
                .subscribe {

                    // Create new master parameter (if needed)
                    if (masterParameters[MasterParameterIndex.VOLUME] == null) {
                        masterParameters[MasterParameterIndex.VOLUME] = MasterParameter(
                                paramIndex = MasterParameterIndex.VOLUME,
                                min = 0f,
                                max = 1f)
                    }

                    masterParameters[MasterParameterIndex.VOLUME]!!.value = it.volume
                    masterParameters[MasterParameterIndex.VOLUME]!!.displayValue = it.displayVolume

                }
                .addTo(compositeDisposable)

        // Current master volume index
        messageManager.oscMessage
                .filter { it.address == LiveAPI.masterVolume }
                .filter { block(LiveParameter.TRACK_VOLUME) }
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

    private fun block(parameter: String): Boolean {
        return !disabledParameters.contains(parameter)
    }

    private fun convertToTrackDisplayVolume(volume: Float): String {

        val volumeDecibels = LiveVolumeUtils.trackVolumeDecibels(volume)

        if (volumeDecibels == -70f) {
            return "-inf dB"
        }

        return "$volumeDecibels dB"
    }

    private fun formattedDisplayVolume(volume: String): String {

        if (volume == "-inf dB") {
            return volume
        }

        val digitsVolume = volume.removeSuffix(" dB").toFloat()
        val roundedVolume = digitsVolume.round(1)
        return "$roundedVolume dB"
    }

    // Mapper functions

    private fun mapToDeviceParameter(oscMessage: OSCMessage): DeviceParameter {

        val trackIndex = oscMessage.arguments[0].int
        val deviceIndex = oscMessage.arguments[1].int
        val paramIndex = oscMessage.arguments[2].int
        val trackName = oscMessage.arguments[3].string
        val deviceName = oscMessage.arguments[4].string
        val paramName = oscMessage.arguments[5].string
        val displayValue = oscMessage.arguments[6].string
        val value = oscMessage.arguments[7].float
        val min = oscMessage.arguments[8].float
        val max = oscMessage.arguments[9].float
        val automationState = oscMessage.arguments[10].int.automationState

        return DeviceParameter(
                trackIndex = trackIndex,
                deviceIndex = deviceIndex,
                paramIndex = paramIndex,
                trackName = trackName,
                deviceName = deviceName,
                paramName = paramName,
                displayValue = displayValue,
                value = value,
                min = min,
                max = max,
                automationState = automationState)
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
                displayVolume = displayVolume)
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
                displayVolume = displayVolume)
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
                automationState = automationState)
    }

    private fun mapToDeviceParameterIndices(oscMessage: OSCMessage): DeviceParameterIndices {

        val trackIndex = oscMessage.arguments[0].int
        val deviceIndex = oscMessage.arguments[1].int
        val paramIndex = oscMessage.arguments[2].int

        return DeviceParameterIndices(
                trackIndex = trackIndex,
                deviceIndex = deviceIndex,
                paramIndex = paramIndex)
    }

    private fun mapToTrackParameterIndices(oscMessage: OSCMessage, trackParamIndex: Int): TrackParameterIndices {

        val trackIndex = oscMessage.arguments[0].int

        return TrackParameterIndices(
                trackIndex = trackIndex,
                paramIndex = trackParamIndex)
    }

    private fun mapToSendIndices(oscMessage: OSCMessage): SendIndices {

        val trackIndex = oscMessage.arguments[0].int
        val sendIndex = oscMessage.arguments[2].int

        return SendIndices(
                trackIndex = trackIndex,
                sendIndex = sendIndex)
    }

}