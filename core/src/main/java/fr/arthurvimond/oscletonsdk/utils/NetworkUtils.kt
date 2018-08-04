package fr.arthurvimond.oscletonsdk.utils

import java.net.NetworkInterface
import java.util.*

/**
 * NetworkUtils is a utility class regarding device connectivity.
 *
 * @since 0.1
 */
object NetworkUtils {

    fun deviceIPAddress(useIPv4: Boolean = true): String {
        try {
            val networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (networkInterface in networkInterfaces) {
                val inetAddresses = Collections.list(networkInterface.inetAddresses)
                for (inetAddress in inetAddresses) {
                    if (!inetAddress.isLoopbackAddress) {
                        val sAddr = inetAddress.hostAddress.toUpperCase()
                        val isIPv4 = sAddr.indexOf(':') < 0
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr
                        } else {
                            if (!isIPv4) {
                                // drop ip6 port suffix
                                val delim = sAddr.indexOf('%')
                                return if (delim < 0) sAddr else sAddr.substring(0, delim)
                            }
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return ""
    }

}