package com.oscleton.sdk.extensions

/**
 * Extension property to convert a [Boolean] to [Int].
 *
 * @since 0.1
 */
val Boolean.int: Int
    get() = if (this) 1 else 0

/**
 * Extension property to convert an [Int] to [Boolean].
 *
 * @since 1.0
 */
val Int.boolean: Boolean
    get() = this == 1