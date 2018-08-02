package fr.arthurvimond.oscletonsdk

import kotlin.jvm.JvmStatic

class OscletonSDK {

    companion object {
        @JvmStatic
        val instance by lazy { OscletonSDK() }
    }

}