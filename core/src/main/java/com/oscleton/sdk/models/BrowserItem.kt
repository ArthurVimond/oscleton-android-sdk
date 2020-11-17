package com.oscleton.sdk.models

import com.oscleton.sdk.utils.BrowserItemUtils

data class BrowserItem(val name: String,
                       val uri: String,
                       val source: String,
                       val isDevice: Boolean,
                       val isFolder: Boolean,
                       val isLoadable: Boolean,
                       val hasChildren: Boolean) {

    enum class Category {
        SOUNDS,
        DRUMS,
        INSTRUMENTS,
        AUDIO_EFFECTS,
        MIDI_EFFECTS,
        MAX_FOR_LIVE,
        PLUG_INS,
        CLIPS,
        SAMPLES,
    }

    val categoryCodeName: String
        get() {
            return nodes.first()
        }

    val category: Category
        get() {
            return when (categoryCodeName) {
                "Sounds" -> Category.SOUNDS
                "Drums" -> Category.DRUMS
                "Synths" -> Category.INSTRUMENTS
                "AudioFx" -> Category.AUDIO_EFFECTS
                "MidiFx" -> Category.MIDI_EFFECTS
                "M4L" -> Category.MAX_FOR_LIVE
                "Plugins" -> Category.PLUG_INS
                "Clips" -> Category.CLIPS
                "Samples" -> Category.SAMPLES
                else -> Category.SOUNDS
            }
        }

    val nodes: List<String>
        get() {
            return BrowserItemUtils.getNodes(uri)
        }

    val parentNodes: List<String>
        get() {
            return nodes.dropLast(1)

        }

}