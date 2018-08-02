package fr.arthurvimond.oscletonsdk.internal

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import fr.arthurvimond.oscletonsdk.utils.Empty
import fr.arthurvimond.oscletonsdk.utils.Logger
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class AppLifecycleObserver : LifecycleObserver {

    // Public properties
    fun rxOnAppCreate(): Observable<Empty> {
        return _onAppCreate
    }

    fun rxOnAppDestroy(): Observable<Empty> {
        return _onAppDestroy
    }

    // Private properties
    private val createdActivityCount = AtomicInteger(0)

    // RxJava
    private val compositeDisposable = CompositeDisposable()

    // App lifecycle
    private val _onAppCreate = PublishSubject.create<Empty>()
    private val _onAppDestroy = PublishSubject.create<Empty>()

    // Activity lifecycle
    private val _onCreate = PublishSubject.create<Empty>()
    private val _onDestroy = PublishSubject.create<Empty>()

    private var canTriggerAppCreate: Boolean = true
    private var canTriggerAppDestroy: Boolean = true

    private val appLifecycleDebounceTime = 400

    init {
        observeProperties()
    }

    private fun observeProperties() {

        compositeDisposable.add(
                _onCreate.subscribe { canTriggerAppDestroy = false }
        )

        compositeDisposable.add(
                _onDestroy.subscribe {
                    canTriggerAppCreate = false
                    canTriggerAppDestroy = true
                }
        )

        compositeDisposable.add(
                _onDestroy
                        .debounce(appLifecycleDebounceTime.toLong(), TimeUnit.MILLISECONDS)
                        .subscribe { canTriggerAppCreate = true }
        )

        // OnAppCreate
        compositeDisposable.add(
                _onCreate
                        .filter { canTriggerAppCreate }
                        .subscribe {
                            // App is created
                            _onAppCreate.onNext(Empty.VOID)
                        }
        )

        // OnAppDestroy
        compositeDisposable.add(
                _onDestroy
                        .debounce(appLifecycleDebounceTime.toLong(), TimeUnit.MILLISECONDS)
                        .filter { canTriggerAppDestroy }
                        .subscribe {
                            // App is destroyed
                            _onAppDestroy.onNext(Empty.VOID)
                        }
        )

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        Logger.i("onCreate", this)
        val count = createdActivityCount.incrementAndGet()
        if (count == 1) {
            _onCreate.onNext(Empty.VOID)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        Logger.i("onStart", this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        Logger.i("onResume", this)
        // Start observers
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        Logger.i("onPause", this)
        // Stop observers
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        Logger.i("onStop", this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        Logger.i("onDestroy", this)
        val count = createdActivityCount.decrementAndGet()
        if (count == 0) {
            _onDestroy.onNext(Empty.VOID)
        }

    }

}