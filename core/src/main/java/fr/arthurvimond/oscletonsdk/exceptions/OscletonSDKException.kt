package fr.arthurvimond.oscletonsdk.exceptions

/**
 * Generic [RuntimeException] specific to Oscleton SDK.
 *
 * @param message The message explaining the reason of the exception.
 */
class OscletonSDKException internal constructor(override var message: String) : RuntimeException()