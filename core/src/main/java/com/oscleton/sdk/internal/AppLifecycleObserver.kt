package com.oscleton.sdk.internal

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.oscleton.sdk.utils.Empty
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class AppLifecycleObserver : LifecycleObserver {

    // Public properties

    fun rxOnAppCreate(): Observable<Empty> {
        return _onAppCreate
    }

    fun rxOnAppForeground(): Observable<Empty> {
        return _onAppForeground
    }

    fun rxOnAppBackground(): Observable<Empty> {
        return _onAppBackground
    }

    // App lifecycle

    private val _onAppCreate: PublishSubject<Empty> = PublishSubject.create()
    private val _onAppForeground: PublishSubject<Empty> = PublishSubject.create()
    private val _onAppBackground: PublishSubject<Empty> = PublishSubject.create()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    internal fun onCreate() {
        _onAppCreate.onNext(Empty.VOID)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    internal fun onMoveToForeground() {
        _onAppForeground.onNext(Empty.VOID)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    internal fun onMoveToBackground() {
        _onAppBackground.onNext(Empty.VOID)
    }

}