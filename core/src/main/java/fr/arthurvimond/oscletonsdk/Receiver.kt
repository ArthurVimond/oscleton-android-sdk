package fr.arthurvimond.oscletonsdk

import fr.arthurvimond.oscletonsdk.di.Injector
import org.koin.standalone.inject

/**
 * Receiver is just a container class to access [ReactiveReceiver] and [CallbackReceiver].
 *
 * @since 0.1
 * @see ReactiveReceiver
 * @see CallbackReceiver
 */
class Receiver internal constructor() {

    private val injector by lazy { Injector() }

    /**
     * In Kotlin, you can directly call [rx] to access [ReactiveReceiver].
     * In Java, you have to call getReactiveReceiver() to access [ReactiveReceiver].
     */
    val rx @JvmName("getReactiveReceiver") get() = injector.inject<ReactiveReceiver>().value

    /**
     * In Kotlin, you can directly call [cb] to access [CallbackReceiver].
     * In Java, you have to call getCallbackReceiver() to access [CallbackReceiver].
     */
    val cb @JvmName("getCallbackReceiver") get() = injector.inject<CallbackReceiver>().value

}