package com.oscleton.sdk

import com.oscleton.sdk.extensions.int
import com.oscleton.sdk.internal.LiveAPI
import com.oscleton.sdk.internal.MessageManager

/**
 * Controller contains the methods to control the current Live set.
 *
 * @since 0.1
 */
class Controller internal constructor(private val messageManager: MessageManager) {

    /**
     * Start playing the current Live set.
     *
     * @since 0.1
     */
    fun play() {
        messageManager.sendMessage(LiveAPI.play)
    }

    /**
     * Stop playing the current Live set.
     *
     * @since 0.1
     */
    fun stop() {
        messageManager.sendMessage(LiveAPI.stop)
    }

    /**
     * Undo the previous action.
     *
     * @since 0.1
     */
    fun undo() {
        messageManager.sendMessage(LiveAPI.undo)
    }

    /**
     * Redo the next action.
     *
     * @since 0.1
     */
    fun redo() {
        messageManager.sendMessage(LiveAPI.redo)
    }

    /**
     * Set the Live set global tempo.
     *
     * @param tempo the global tempo to set
     * @since 0.1
     */
    fun setTempo(tempo: Float) {
        messageManager.sendMessage(LiveAPI.tempo, listOf(tempo))
    }

    /**
     * Set the Live set metronome.
     *
     * @param enabled the value for the metronome to be set
     * @since 0.1
     */
    fun setMetronome(enabled: Boolean) {
        messageManager.sendMessage(LiveAPI.metronome, listOf(enabled.int))
    }

    /**
     * Set the Live set overdub.
     *
     * @param enabled the value for the overdub to be set
     * @since 0.1
     */
    fun setOverdub(enabled: Boolean) {
        messageManager.sendMessage(LiveAPI.overdub, listOf(enabled.int))
    }

}