package com.oscleton.sdk.internal

internal object LiveAPI {

    // Prefix
    private const val live = "/live/"

    // Config
    const val start = live + "start"
    const val quit = live + "quit"
    const val liveVersion = live + "config/live_version"
    const val scriptVersion = live + "config/script_version"
    const val setPeer = live + "set_peer"
    const val setPeerSuccess = live + "set_peer/success"
    const val discoverIP = live + "config/discover_ip"
    const val discoverIPSuccess = live + "config/discover_ip/success"
    const val setAppTrack = live + "config/app_track"
    const val setAppPlatform = live + "config/app_platform"

    // Transport

    const val tempo = live + "tempo"
    const val time = live + "time"

    const val play = live + "play"
    const val playContinue = live + "play/continue"
    const val playSelect = live + "play/select"
    const val stop = live + "stop"

    const val cuePrev = live + "cue/prev"
    const val cueNext = live + "cue/next"

    const val undo = live + "undo"
    const val redo = live + "redo"

    const val overdub = live + "overdub"
    const val metronome = live + "metronome"
    const val captureMidi = live + "capture_midi"
    const val canCaptureMidi = live + "can_capture_midi"
    const val loop = live + "loop"
    const val signature = live + "signature"


    // Tracks
    const val trackCount = live + "tracks"
    const val trackName = live + "track/name"
    const val trackArm = live + "track/arm"
    const val trackMute = live + "track/mute"
    const val trackSolo = live + "track/solo"
    const val trackVolume = live + "track/volume"
    const val trackPanning = live + "track/panning"
    const val trackColor = live + "track/color"
    const val trackSend = live + "track/send"
    const val trackCrossfader = live + "track/crossfader"

    const val selectTrack = live + "track/select"
    const val trackState = live + "track/state"
    const val trackStop = live + "track/stop"


    // Returns
    const val returnName = live + "return/name"
    const val returnColor = live + "return/color"
    const val returnArm = live + "return/arm"
    const val returnMute = live + "return/mute"
    const val returnSolo = live + "return/solo"
    const val returnVolume = live + "return/volume"
    const val returnPanning = live + "return/panning"
    const val returnSend = live + "return/send"
    const val returnCrossfader = live + "return/crossfader"


    // Master
    const val masterArm = live + "master/arm"
    const val masterMute = live + "master/mute"
    const val masterSolo = live + "master/solo"
    const val masterVolume = live + "master/volume"
    const val masterPanning = live + "master/panning"
    const val masterSend = live + "master/send"
    const val masterCrossfader = live + "master/crossfader"

    const val selectMaster = live + "master/select"


    // Devices
    const val trackDevices = live + "track/devices"
    const val returnDevices = live + "return/devices"
    const val masterDevices = live + "master/devices"

    const val trackDeviceRange = live + "track/device/range"
    const val returnDeviceRange = live + "return/device/range"
    const val masterDeviceRange = live + "master/device/range"

    const val trackDeviceParam = live + "track/device/param"
    const val returnDeviceParam = live + "return/device/param"
    const val masterDeviceParam = live + "master/device/param"

    const val selectTrackDevice = live + "track/device/select"
    const val selectReturnDevice = live + "return/device/select"
    const val selectMasterDevice = live + "master/device/select"


    // Scenes
    const val sceneCount = live + "scenes"
    const val sceneName = live + "scene/name"
    const val sceneNameBlock = live + "scene/name/block"
    const val sceneColor = live + "scene/color"
    const val sceneState = live + "scene/state"

    const val selectscene = live + "scene/select"


    // Clips
    const val clipState = live + "clip/state"
    const val clipPlay = live + "clip/play"
    const val clipStop = live + "clip/stop"
    const val clipView = live + "clip/view"
    const val clipName = live + "clip/name"
    const val clipNameBlock = live + "clip/name/block"
    const val clipColor = live + "clip/color"

    const val clipLooping = live + "clip/looping"
    const val clipLoopStart = live + "clip/loopstart"
    const val clipLoopEnd = live + "clip/loopend"
    const val clipLoopJump = live + "clip/loopjump"
    const val clipStart = live + "clip/start"
    const val clipEnd = live + "clip/end"
    const val clipWarping = live + "clip/warping"
    const val clipPitch = live + "clip/pitch"

    const val createClip = live + "clip/create"
    const val deleteClip = live + "clip/delete"

    // Browser
    const val browserLoadBrowserItem = live + "browser/load_browser_item"
    const val browserGetBrowserItemChildren = live + "browser/get_browser_item_children"
    const val browserBrowserItemChildren = live + "browser/browser_item_children"
    const val browserPreviewBrowserItem = live + "browser/preview_browser_item"
    const val browserStopPreview = live + "browser/stop_preview"

}