package fr.arthurvimond.oscletonsdk.internal

import com.illposed.osc.OSCMessage
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

internal class LiveSetDataManager {

    // Private properties

    // General

    private val _tempo: BehaviorSubject<Float> = BehaviorSubject.create()

    // Public properties

    val neededOSCMessage: PublishSubject<OSCMessage> = PublishSubject.create()

    // General

    val tempo: Observable<Float> = _tempo




}