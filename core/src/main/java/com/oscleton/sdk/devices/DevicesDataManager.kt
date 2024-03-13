package com.oscleton.sdk.devices

import com.illposed.osc.OSCMessage
import com.oscleton.sdk.enums.LiveParameter
import com.oscleton.sdk.extensions.automationState
import com.oscleton.sdk.extensions.float
import com.oscleton.sdk.extensions.int
import com.oscleton.sdk.extensions.string
import com.oscleton.sdk.internal.CommonDataManager
import com.oscleton.sdk.internal.LiveAPI
import com.oscleton.sdk.internal.MessageManager
import com.oscleton.sdk.models.Device
import com.oscleton.sdk.models.DeviceParameter
import com.oscleton.sdk.models.DeviceParameterIndices
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class DevicesDataManager internal constructor(
    private val messageManager: MessageManager,
    private val commonDataManager: CommonDataManager
) {

    // Public properties

    val trackDeviceParameter: Observable<DeviceParameter>
        get() = _trackDeviceParameter

    val returnDeviceParameter: Observable<DeviceParameter>
        get() = _returnDeviceParameter

    val masterDeviceParameter: Observable<DeviceParameter>
        get() = _masterDeviceParameter

    // Private properties

    private val _trackDeviceParameter: PublishSubject<DeviceParameter> = PublishSubject.create()
    private val _returnDeviceParameter: PublishSubject<DeviceParameter> = PublishSubject.create()
    private val _masterDeviceParameter: PublishSubject<DeviceParameter> = PublishSubject.create()

    private val compositeDisposable = CompositeDisposable()

    private val trackDevices: MutableMap<Pair<Int, Int>, Device> = mutableMapOf()
    private val trackDeviceParameters: MutableMap<Triple<Int, Int, Int>, DeviceParameter> =
        mutableMapOf()
    private val returnDevices: MutableMap<Pair<Int, Int>, Device> = mutableMapOf()
    private val returnDeviceParameters: MutableMap<Triple<Int, Int, Int>, DeviceParameter> =
        mutableMapOf()
    private val masterDevices: MutableMap<Pair<Int, Int>, Device> = mutableMapOf()
    private val masterDeviceParameters: MutableMap<Triple<Int, Int, Int>, DeviceParameter> =
        mutableMapOf()

    private val currentTrackDeviceParameterIndices: PublishSubject<DeviceParameterIndices> =
        PublishSubject.create()
    private val currentReturnDeviceParameterIndices: PublishSubject<DeviceParameterIndices> =
        PublishSubject.create()

    private val currentMasterDeviceParameterIndices: PublishSubject<DeviceParameterIndices> =
        PublishSubject.create()

    init {
        observeTrackDeviceParametersProperties()
        observeReturnDeviceParametersProperties()
        observeMasterDeviceParametersProperties()
    }

    private fun observeTrackDeviceParametersProperties() {

        // Fill the maps on track device parameters changes
        messageManager.oscMessage
            .filter { it.address == LiveAPI.trackDeviceParam }
            .filter { commonDataManager.block(LiveParameter.DEVICE_PARAMETER) }
            .map { mapToDeviceParameter(it) }
            .subscribe {

                // Create new device (if needed)
                val pair = Pair(it.trackIndex, it.deviceIndex)
                if (trackDevices[pair] == null) {
                    trackDevices[pair] = Device(it.trackIndex, it.deviceIndex)
                }

                val updatedDevice = trackDevices[pair]!!.copy(
                    trackIndex = it.trackIndex,
                    deviceIndex = it.deviceIndex
                )
                trackDevices[pair] = updatedDevice

                // Track device parameter
                val triple = Triple(it.trackIndex, it.deviceIndex, it.paramIndex)
                trackDeviceParameters[triple] = it

            }.addTo(compositeDisposable)

        // Current track device parameter indices
        messageManager.oscMessage
            .filter { it.address == LiveAPI.trackDeviceParam }
            .filter { commonDataManager.block(LiveParameter.DEVICE_PARAMETER) }
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

    private fun observeReturnDeviceParametersProperties() {

        // Fill the maps on return device parameters changes
        messageManager.oscMessage
            .filter { it.address == LiveAPI.returnDeviceParam }
            .filter { commonDataManager.block(LiveParameter.DEVICE_PARAMETER) }
            .map { mapToDeviceParameter(it) }
            .subscribe {

                // Create new device (if needed)
                val pair = Pair(it.trackIndex, it.deviceIndex)
                if (returnDevices[pair] == null) {
                    returnDevices[pair] = Device(it.trackIndex, it.deviceIndex)
                }

                val updatedDevice = returnDevices[pair]!!.copy(
                    trackIndex = it.trackIndex,
                    deviceIndex = it.deviceIndex
                )

                returnDevices[pair] = updatedDevice

                // Return device parameter
                val triple = Triple(it.trackIndex, it.deviceIndex, it.paramIndex)
                returnDeviceParameters[triple] = it

            }.addTo(compositeDisposable)

        // Current return device parameter indices
        messageManager.oscMessage
            .filter { it.address == LiveAPI.returnDeviceParam }
            .filter { commonDataManager.block(LiveParameter.DEVICE_PARAMETER) }
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

    private fun observeMasterDeviceParametersProperties() {

        // Fill the maps on master device parameters changes
        messageManager.oscMessage
            .filter { it.address == LiveAPI.masterDeviceParam }
            .filter { commonDataManager.block(LiveParameter.DEVICE_PARAMETER) }
            .map { mapToDeviceParameter(it) }
            .subscribe {

                // Create new device (if needed)
                val pair = Pair(it.trackIndex, it.deviceIndex)
                if (masterDevices[pair] == null) {
                    masterDevices[pair] = Device(it.trackIndex, it.deviceIndex)
                }

                val updatedDevice = masterDevices[pair]!!.copy(
                    trackIndex = it.trackIndex,
                    deviceIndex = it.deviceIndex
                )

                masterDevices[pair] = updatedDevice

                // Master device parameter
                val triple = Triple(it.trackIndex, it.deviceIndex, it.paramIndex)
                masterDeviceParameters[triple] = it

            }.addTo(compositeDisposable)

        // Current master device parameter indices
        messageManager.oscMessage
            .filter { it.address == LiveAPI.masterDeviceParam }
            .filter { commonDataManager.block(LiveParameter.DEVICE_PARAMETER) }
            .map { mapToDeviceParameterIndices(it) }
            .subscribe { currentMasterDeviceParameterIndices.onNext(it) }
            .addTo(compositeDisposable)

        // Emit full master device parameter info from map
        currentMasterDeviceParameterIndices
            .subscribe {
                val triple = Triple(it.trackIndex, it.deviceIndex, it.paramIndex)
                val deviceParam = masterDeviceParameters.getValue(triple)
                _masterDeviceParameter.onNext(deviceParam)
            }
            .addTo(compositeDisposable)

    }

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
            automationState = automationState
        )
    }

    private fun mapToDeviceParameterIndices(oscMessage: OSCMessage): DeviceParameterIndices {

        val trackIndex = oscMessage.arguments[0].int
        val deviceIndex = oscMessage.arguments[1].int
        val paramIndex = oscMessage.arguments[2].int

        return DeviceParameterIndices(
            trackIndex = trackIndex,
            deviceIndex = deviceIndex,
            paramIndex = paramIndex
        )
    }

}