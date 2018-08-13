package fr.arthurvimond.oscletonsdk.internal

import com.illposed.osc.OSCMessage
import fr.arthurvimond.oscletonsdk.extensions.float
import fr.arthurvimond.oscletonsdk.extensions.int
import fr.arthurvimond.oscletonsdk.extensions.string
import fr.arthurvimond.oscletonsdk.models.Device
import fr.arthurvimond.oscletonsdk.models.DeviceParameter
import fr.arthurvimond.oscletonsdk.models.DeviceParameterIndices
import fr.arthurvimond.oscletonsdk.models.Track
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

internal class LiveSetDataManager internal constructor(private val messageManager: MessageManager) {

    // Public properties

    // General

    val liveVersion: Observable<String> = messageManager.oscMessage
            .filter { it.address == LiveAPI.liveVersion }
            .map { it.arguments.first().string }

    val scriptVersion: Observable<String> = messageManager.oscMessage
            .filter { it.address == LiveAPI.scriptVersion }
            .map { it.arguments.first().string }

    val tempo: Observable<Float> = messageManager.oscMessage
            .filter { it.address == LiveAPI.tempo }
            .map { it.arguments.first().float }


    // Device parameters
    val deviceParameter: Observable<DeviceParameter>
        get() = _deviceParameter

    // Private properties

    private val _deviceParameter: PublishSubject<DeviceParameter> = PublishSubject.create()

    // RxJava
    private val compositeDisposable = CompositeDisposable()

    private val tracks: MutableMap<Int, Track> = mutableMapOf()
    private val devices: MutableMap<Pair<Int, Int>, Device> = mutableMapOf()
    private val deviceParameters: MutableMap<Triple<Int, Int, Int>, DeviceParameter> = mutableMapOf()

    private val currentDeviceParameterIndices: PublishSubject<DeviceParameterIndices> = PublishSubject.create()

    init {
        observeProperties()
    }

    private fun observeProperties() {

        // Fill the maps on device parameters changes
        messageManager.oscMessage
                .filter { it.address == LiveAPI.trackDeviceParam }
                .map { mapToDeviceParameter(it) }
                .subscribe {

                    // Track
                    tracks[it.trackIndex] = Track(it.trackIndex)

                    // Device
                    val pair = Pair(it.trackIndex, it.deviceIndex)
                    devices[pair] = Device(it.trackIndex, it.deviceIndex)

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
        val name = oscMessage.arguments[3].string
        val value = oscMessage.arguments[4].float
        val min = oscMessage.arguments[5].float
        val max = oscMessage.arguments[6].float

        return DeviceParameter(
                paramIndex = paramIndex,
                deviceIndex = deviceIndex,
                trackIndex = trackIndex,
                name = name,
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