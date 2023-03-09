package io.mths.kava.util

/**
 * Returns the result of [supply] if it isn't null.
 * If it's null, the receiver will be returned.
 */
inline infix fun <Type, ReceiverType, ReturnType> ReceiverType.whenNot(
    supply: ReceiverType.() -> Type?
): ReturnType where Type : ReturnType, ReceiverType : ReturnType =
    supply() ?: this