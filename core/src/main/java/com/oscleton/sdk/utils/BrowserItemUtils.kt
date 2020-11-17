package com.oscleton.sdk.utils

internal object BrowserItemUtils {

    fun getNodes(uri: String): List<String> {
        return uri
                .removePrefix("query:")
                .split("#", ":")
                .map { it.replace("%20", " ") }
    }

}