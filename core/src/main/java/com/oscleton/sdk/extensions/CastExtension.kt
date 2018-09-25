package com.oscleton.sdk.extensions

/**
 * Extension property to cast an [Any] to [Boolean].
 *
 * @since 0.2
 */
val Any.boolean: Boolean
    get() = this as Boolean

/**
 * Extension property to cast an [Any] to [Int].
 *
 * @since 0.1
 */
val Any.int: Int
    get() = this as Int

/**
 * Extension property to cast an [Any] to [Float].
 *
 * @since 0.1
 */
val Any.float: Float
    get() = this as Float

/**
 * Extension property to cast an [Any] to [String].
 *
 * @since 0.1
 */
val Any.string: String
    get() = this as String

/**
 * Extension property to cast a MutableList of [Any] to a MutableList of [String].
 *
 * @since 0.1
 */
val MutableList<Any>.string: MutableList<String>
    get() = this as MutableList<String>