package com.oscleton.sdk.internal

import com.illposed.osc.OSCMessage
import com.oscleton.sdk.enums.LiveParameter
import com.oscleton.sdk.enums.ReturnParameterIndex
import com.oscleton.sdk.enums.TrackParameterIndex
import com.oscleton.sdk.extensions.float
import com.oscleton.sdk.extensions.int
import com.oscleton.sdk.extensions.string
import com.oscleton.sdk.models.*
import com.oscleton.sdk.utils.Empty
import com.oscleton.sdk.utils.LiveVolumeUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

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
    val deviceParameter: Observable<DeviceParameter>
        get() = _deviceParameter

    val trackParameter: Observable<TrackParameter>
        get() = _trackParameter

    val returnParameter: Observable<ReturnParameter>
        get() = _returnParameter

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

    private val _deviceParameter: PublishSubject<DeviceParameter> = PublishSubject.create()
    private val _trackParameter: PublishSubject<TrackParameter> = PublishSubject.create()
    private val _returnParameter: PublishSubject<ReturnParameter> = PublishSubject.create()

    // RxJava
    private val compositeDisposable = CompositeDisposable()

    private val trackParameters: MutableMap<Int, TrackParameter> = mutableMapOf()
    private val returnParameters: MutableMap<Int, ReturnParameter> = mutableMapOf()
    private val devices: MutableMap<Pair<Int, Int>, Device> = mutableMapOf()
    private val deviceParameters: MutableMap<Triple<Int, Int, Int>, DeviceParameter> = mutableMapOf()

    private val currentDeviceParameterIndices: PublishSubject<DeviceParameterIndices> = PublishSubject.create()
    private val currentTrackParameterIndices: PublishSubject<TrackParameterIndices> = PublishSubject.create()
    private val currentReturnParameterIndices: PublishSubject<TrackParameterIndices> = PublishSubject.create()

    private val disabledParameters: MutableSet<String> = mutableSetOf()

    init {
        observeConfigProperties()
        observeTransportProperties()
        observeDeviceParametersProperties()
        observeTrackVolumeProperties()
        observeReturnVolumeProperties()
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

    private fun observeDeviceParametersProperties() {

        // Fill the maps on device parameters changes
        messageManager.oscMessage
                .filter { it.address == LiveAPI.trackDeviceParam }
                .filter { block(LiveParameter.DEVICE_PARAMETER) }
                .map { mapToDeviceParameter(it) }
                .subscribe {

                    // Create new device (if needed)
                    val pair = Pair(it.trackIndex, it.deviceIndex)
                    if (devices[pair] == null) {
                        devices[pair] = Device(it.trackIndex, it.deviceIndex)
                    }

                    devices[pair]!!.trackIndex = it.trackIndex
                    devices[pair]!!.deviceIndex = it.deviceIndex

                    // Device parameter
                    val triple = Triple(it.trackIndex, it.deviceIndex, it.paramIndex)
                    deviceParameters[triple] = it

                }.addTo(compositeDisposable)

        // Current device parameter indices
        messageManager.oscMessage
                .filter { it.address == LiveAPI.trackDeviceParam }
                .filter { block(LiveParameter.DEVICE_PARAMETER) }
                .map { mapToDeviceParameterIndices(it) }
                .subscribe { currentDeviceParameterIndices.onNext(it) }
                .addTo(compositeDisposable)

        // Emit full device parameter info from map
        currentDeviceParameterIndices
                .subscribe {
                    val triple = Triple(it.trackIndex, it.deviceIndex, it.paramIndex)
                    val deviceParam = deviceParameters.getValue(triple)
                    _deviceParameter.onNext(deviceParam)
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
                .filter { it.address == LiveAPI.masterVolume}
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
        val automationState = oscMessage.arguments[10].int

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

}