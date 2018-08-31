package fr.arthurvimond.oscletonsdk.internal

import com.illposed.osc.OSCMessage
import fr.arthurvimond.oscletonsdk.extensions.float
import fr.arthurvimond.oscletonsdk.extensions.int
import fr.arthurvimond.oscletonsdk.extensions.string
import fr.arthurvimond.oscletonsdk.models.Device
import fr.arthurvimond.oscletonsdk.models.DeviceParameter
import fr.arthurvimond.oscletonsdk.models.DeviceParameterIndices
import fr.arthurvimond.oscletonsdk.models.Track
import fr.arthurvimond.oscletonsdk.utils.Empty
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

internal class LiveSetDataManager internal constructor(private val messageManager: MessageManager) {

    // Public properties

    // General

    val liveVersion: Observable<String>
        get() = _liveVersion

    val scriptVersion: Observable<String>
        get() = _scriptVersion

    val onSetPeerSuccess: Observable<Empty>
        get() = _onSetPeerSuccess

    val tempo: Observable<Float>
        get() = _tempo


    // Device parameters
    val deviceParameter: Observable<DeviceParameter>
        get() = _deviceParameter

    // Private properties

    private val _liveVersion: BehaviorSubject<String> = BehaviorSubject.create()
    private val _scriptVersion: BehaviorSubject<String> = BehaviorSubject.create()
    private val _onSetPeerSuccess: PublishSubject<Empty> = PublishSubject.create()

    private val _tempo: BehaviorSubject<Float> = BehaviorSubject.create()

    private val _deviceParameter: PublishSubject<DeviceParameter> = PublishSubject.create()

    // RxJava
    private val compositeDisposable = CompositeDisposable()

    private val tracks: MutableMap<Int, Track> = mutableMapOf()
    private val devices: MutableMap<Pair<Int, Int>, Device> = mutableMapOf()
    private val deviceParameters: MutableMap<Triple<Int, Int, Int>, DeviceParameter> = mutableMapOf()

    private val currentDeviceParameterIndices: PublishSubject<DeviceParameterIndices> = PublishSubject.create()

    init {
        observeConfigProperties()
        observeTransportProperties()
        observeDeviceParametersProperties()
    }

    private fun observeConfigProperties() {

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
                .map { mapToDeviceParameter(it) }
                .subscribe {

                    // Create new track (if needed)
                    if (tracks[it.trackIndex] == null) {
                        tracks[it.trackIndex] = Track(it.trackIndex)
                    }

                    tracks[it.trackIndex]!!.index = it.trackIndex

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
                max = max)
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

}