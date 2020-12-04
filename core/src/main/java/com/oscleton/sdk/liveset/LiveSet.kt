package com.oscleton.sdk.liveset

import com.oscleton.sdk.extensions.int
import com.oscleton.sdk.internal.LiveAPI
import com.oscleton.sdk.internal.MessageManager

class LiveSet internal constructor(private val liveSetDataManager: LiveSetDataManager,
                                   private val messageManager: MessageManager) {

    /**
     * Start playing the current Live set.
     *
     * @since 1.0
     */
    fun play() {
        messageManager.sendMessage(LiveAPI.play)
    }

    /**
     * Stop playing the current Live set.
     *
     * @since 1.0
     */
    fun stop() {
        messageManager.sendMessage(LiveAPI.stop)
    }

    /**
     * Undo the previous action.
     *
     * @since 1.0
     */
    fun undo() {
        messageManager.sendMessage(LiveAPI.undo)
    }

    /**
     * Redo the next action.
     *
     * @since 1.0
     */
    fun redo() {
        messageManager.sendMessage(LiveAPI.redo)
    }

    /**
     * Set the Live set global tempo.
     *
     * @param tempo the global tempo to set
     * @since 1.0
     */
    fun setTempo(tempo: Float) {
        messageManager.sendMessage(LiveAPI.tempo, listOf(tempo))
    }

    /**
     * Set the Live set metronome.
     *
     * @param enabled the value for the metronome to be set
     * @since 1.0
     */
    fun setMetronome(enabled: Boolean) {
        messageManager.sendMessage(LiveAPI.metronome, listOf(enabled.int))
    }

    /**
     * Set the Live set overdub.
     *
     * @param enabled the value for the overdub to be set
     * @since 1.0
     */
    fun setOverdub(enabled: Boolean) {
        messageManager.sendMessage(LiveAPI.overdub, listOf(enabled.int))
    }

    /**
     * Capture MIDI.
     *
     * @since 1.0
     */
    fun captureMidi() {
        messageManager.sendMessage(LiveAPI.captureMidi)
    }

}