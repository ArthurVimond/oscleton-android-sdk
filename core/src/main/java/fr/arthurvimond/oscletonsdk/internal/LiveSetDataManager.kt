package fr.arthurvimond.oscletonsdk.internal

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

internal class LiveSetDataManager {

    // Private properties

    // General

    private val _tempo: BehaviorSubject<Float> = BehaviorSubject.create()

    // Public properties

    // General

    val tempo: Observable<Float> = _tempo




}