package io.mths.kava.compiler

import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.ClassId

object IllegalState {

    fun noConstructorFor(classId: ClassId) =
        because("No constructor found for $classId!")

    fun noCallableSymbolFor(callableId: CallableId) =
        because("No callable symbol found for $callableId!")
}

fun because(reason: String) =
    IllegalStateException(reason)