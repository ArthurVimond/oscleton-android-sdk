package com.oscleton.sdk.internal

internal class CommonDataManager {

    private val disabledParameters: MutableSet<String> = mutableSetOf()

    fun disableParameters(parameters: List<String>) {
        disabledParameters.clear()
        disabledParameters.addAll(parameters)
    }

    fun enableAllParameters() {
        disabledParameters.clear()
    }

    fun block(parameter: String): Boolean {
        return !disabledParameters.contains(parameter)
    }

}