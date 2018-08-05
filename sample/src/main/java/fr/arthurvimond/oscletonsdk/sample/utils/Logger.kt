package fr.arthurvimond.oscletonsdk.sample.utils

import android.util.Log
import fr.arthurvimond.oscletonsdk.BuildConfig

object Logger {

    private var logEnabled = true

    @JvmStatic fun d(message: String, caller: Any? = "") {
        val tag = if (caller !is String) caller?.javaClass?.simpleName else "Logger"
        if (logEnabled && BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    @JvmStatic fun i(message: String, caller: Any? = "") {
        val tag = if (caller !is String) caller?.javaClass?.simpleName else "Logger"
        if (logEnabled && BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }

    @JvmStatic fun w(message: String, caller: Any? = "") {
        val tag = if (caller !is String) caller?.javaClass?.simpleName else "Logger"
        if (logEnabled && BuildConfig.DEBUG) {
            Log.w(tag, message)
        }
    }

    @JvmStatic fun e(message: String, caller: Any? = "") {
        val tag = if (caller !is String) caller?.javaClass?.simpleName else "Logger"
        if (logEnabled && BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }

    @JvmStatic fun v(message: String, caller: Any? = "") {
        val tag = if (caller !is String) caller?.javaClass?.simpleName else "Logger"
        if (logEnabled && BuildConfig.DEBUG) {
            Log.v(tag, message)
        }
    }

}