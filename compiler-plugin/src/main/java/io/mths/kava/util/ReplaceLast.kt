package io.mths.kava.util

fun <Type> List<Type>.replaceLast(
    replacement: Type
): List<Type> {
    val newList = dropLast(1)

    return newList + replacement
}