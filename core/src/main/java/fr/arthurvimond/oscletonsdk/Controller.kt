package fr.arthurvimond.oscletonsdk

import fr.arthurvimond.oscletonsdk.extensions.int
import fr.arthurvimond.oscletonsdk.internal.LiveAPI
import fr.arthurvimond.oscletonsdk.internal.MessageManager

/**
 * Controller contains the methods to control the current Live set.
 *
 * @since 0.1
 */
class Controller internal constructor(private val messageManager: MessageManager) {

    fun play() {
        messageManager.sendMessage(LiveAPI.play)
    }

    fun stop() {
        messageManager.sendMessage(LiveAPI.stop)
    }

    fun undo() {
        messageManager.sendMessage(LiveAPI.undo)
    }

    fun redo() {
        messageManager.sendMessage(LiveAPI.redo)
    }

    fun setTempo(tempo: Float) {
        messageManager.sendMessage(LiveAPI.tempo, listOf(tempo))
    }

    fun setMetronome(enabled: Boolean) {
        messageManager.sendMessage(LiveAPI.metronome, listOf(enabled.int))
    }

    fun setOverdub(enabled: Boolean) {
        messageManager.sendMessage(LiveAPI.overdub, listOf(enabled.int))
    }

}