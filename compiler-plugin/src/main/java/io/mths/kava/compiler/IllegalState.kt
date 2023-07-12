package io.mths.kava.compiler

import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.ClassId

object IllegalState {
    fun noPropertyFor(callableId: CallableId) =
        because("No property found for $callableId")
    fun noClassFor(classId: ClassId) =
        because("No class found for $classId")

    fun noConstructorFor(classId: ClassId) =
        because("No constructor found for $classId!")

    fun noCallableSymbolFor(callableId: CallableId) =
        because("No callable symbol found for $callableId!")
}

fun IllegalState.because(reason: String) =
    IllegalStateException(reason)