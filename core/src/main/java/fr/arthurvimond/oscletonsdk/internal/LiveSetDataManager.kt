package fr.arthurvimond.oscletonsdk.internal

import fr.arthurvimond.oscletonsdk.extensions.float
import io.reactivex.Observable

internal class LiveSetDataManager internal constructor(messageManager: MessageManager) {

    // Public properties

    // General

    val tempo: Observable<Float> = messageManager.oscMessage
            .filter { it.address == LiveAPI.tempo }
            .map { it.arguments.first().float }

}