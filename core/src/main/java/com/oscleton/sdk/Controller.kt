package com.oscleton.sdk

import com.oscleton.sdk.extensions.int
import com.oscleton.sdk.internal.LiveAPI
import com.oscleton.sdk.internal.MessageManager

/**
 * Controller contains the methods to control the current Live set.
 *
 * @since 0.1
 */
@Deprecated(
        message = "Use LiveSet instead",
        replaceWith = ReplaceWith("LiveSet"))
class Controller internal constructor(private val messageManager: MessageManager) {

    /**
     * Start playing the current Live set.
     *
     * @since 0.1
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.liveSet.play() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.liveSet.play()"))
    fun play() {
        messageManager.sendMessage(LiveAPI.play)
    }

    /**
     * Stop playing the current Live set.
     *
     * @since 0.1
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.liveSet.stop() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.liveSet.stop()"))
    fun stop() {
        messageManager.sendMessage(LiveAPI.stop)
    }

    /**
     * Undo the previous action.
     *
     * @since 0.1
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.liveSet.undo() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.liveSet.undo()"))
    fun undo() {
        messageManager.sendMessage(LiveAPI.undo)
    }

    /**
     * Redo the next action.
     *
     * @since 0.1
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.liveSet.redo() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.liveSet.redo()"))
    fun redo() {
        messageManager.sendMessage(LiveAPI.redo)
    }

    /**
     * Set the Live set global tempo.
     *
     * @param tempo the global tempo to set
     * @since 0.1
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.liveSet.setTempo() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.liveSet.setTempo(tempo)"))
    fun setTempo(tempo: Float) {
        messageManager.sendMessage(LiveAPI.tempo, listOf(tempo))
    }

    /**
     * Set the Live set metronome.
     *
     * @param enabled the value for the metronome to be set
     * @since 0.1
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.liveSet.setMetronome() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.liveSet.setMetronome(enabled)"))
    fun setMetronome(enabled: Boolean) {
        messageManager.sendMessage(LiveAPI.metronome, listOf(enabled.int))
    }

    /**
     * Set the Live set overdub.
     *
     * @param enabled the value for the overdub to be set
     * @since 0.1
     */
    @Deprecated(
            message = "Use OscletonSDK.instance.liveSet.setOverdub() instead",
            replaceWith = ReplaceWith("OscletonSDK.instance.liveSet.setOverdub(enabled)"))
    fun setOverdub(enabled: Boolean) {
        messageManager.sendMessage(LiveAPI.overdub, listOf(enabled.int))
    }

}