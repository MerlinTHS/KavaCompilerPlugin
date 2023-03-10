package io.mths.kava.util

/**
 * Performs the given [action] in the context of each element.
 */
fun <Type> Array<out Type>.withEach(
    action: Type.() -> Unit
) {
    forEach(action)
}

/**
 * Performs the given [action] in the context of each element.
 */
fun <Type> Collection<Type>.withEach(
    action: Type.() -> Unit
) {
    forEach(action)
}