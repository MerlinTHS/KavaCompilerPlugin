package io.mths.kava.compiler

import org.jetbrains.kotlin.name.CallableId

object IllegalState {
    object Frontend {
        fun noSingleStringConstructor() =
            because("No constructor with single argument of type String found!")
    }

    fun noSymbolFor(callableId: CallableId) =
        because("No callable symbol found for $callableId!")

    fun because(reason: String) =
        IllegalStateException(reason)
}