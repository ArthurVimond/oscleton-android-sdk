package com.oscleton.sdk.exceptions

/**
 * Generic [RuntimeException] specific to Oscleton SDK.
 *
 * @param message The message explaining the reason of the exception.
 * @since 0.1
 */
class OscletonSDKException internal constructor(override var message: String) : RuntimeException()