package com.oscleton.sdk

import com.oscleton.sdk.di.Injector
import org.koin.core.inject

/**
 * Receiver is just a container class to access [ReactiveReceiver] and [CallbackReceiver].
 *
 * @since 0.1
 * @see ReactiveReceiver
 * @see CallbackReceiver
 */
@Deprecated(message = "Use corresponding rx() or cb() extension functions from the 'core-rxjava2' or 'core-callbacks' artifacts instead")
class Receiver internal constructor() {

    private val injector by lazy { Injector() }

    /**
     * In Kotlin, you can directly call [rx] to access [ReactiveReceiver].
     * In Java, you have to call getReactiveReceiver() to access [ReactiveReceiver].
     */
    @Deprecated(message = "Use corresponding rx() extension functions from the 'core-rxjava2' artifact instead")
    val rx @JvmName("getReactiveReceiver") get() = injector.inject<ReactiveReceiver>().value

    /**
     * In Kotlin, you can directly call [cb] to access [CallbackReceiver].
     * In Java, you have to call getCallbackReceiver() to access [CallbackReceiver].
     */
    @Deprecated(message = "Use corresponding cb() extension functions from the 'core-callbacks' artifact instead")
    val cb @JvmName("getCallbackReceiver") get() = injector.inject<CallbackReceiver>().value

}