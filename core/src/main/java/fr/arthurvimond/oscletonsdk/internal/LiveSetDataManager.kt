package fr.arthurvimond.oscletonsdk.internal

import com.illposed.osc.OSCMessage
import fr.arthurvimond.oscletonsdk.extensions.float
import fr.arthurvimond.oscletonsdk.extensions.int
import fr.arthurvimond.oscletonsdk.extensions.string
import fr.arthurvimond.oscletonsdk.models.DeviceParameter
import io.reactivex.Observable

internal class LiveSetDataManager internal constructor(messageManager: MessageManager) {

    // Public properties

    // General

    val tempo: Observable<Float> = messageManager.oscMessage
            .filter { it.address == LiveAPI.tempo }
            .map { it.arguments.first().float }


    // Device parameters

    val deviceParameter: Observable<DeviceParameter> = messageManager.oscMessage
            .filter { it.address == LiveAPI.trackDeviceParam }
            .map { mapToDeviceParameter(it) }


    // Private properties

    // RxJava
//    private val compositeDisposable = CompositeDisposable()

    // Device parameters
//    private val deviceParameters: MutableMap<Triple<Int, Int, Int>, DeviceParameter> = mutableMapOf()

    init {
//        observeProperties()
    }

//    private fun observeProperties() {
//
//        // Device parameters
//        messageManager.oscMessage
//                .filter { it.address == LiveAPI.trackDeviceParam }
//                .map { mapToDeviceParameter(it) }
//                .subscribe {
//                    val triple = Triple(it.trackIndex, it.deviceIndex, it.paramIndex)
//                    deviceParameters[triple] = it
//                }.addTo(compositeDisposable)
//
//    }


    // Mapper functions

    private fun mapToDeviceParameter(oscMessage: OSCMessage): DeviceParameter {

        val trackIndex = oscMessage.arguments[0].int
        val deviceIndex = oscMessage.arguments[1].int
        val paramIndex = oscMessage.arguments[2].int
        val value = oscMessage.arguments[3].float
        val name = oscMessage.arguments[4].string

        val deviceParameter = DeviceParameter(
                paramIndex = paramIndex,
                deviceIndex = deviceIndex,
                trackIndex = trackIndex,
                value = value,
                name = name
        )

        return deviceParameter
    }


}