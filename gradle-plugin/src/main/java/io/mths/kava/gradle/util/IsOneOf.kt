package io.mths.kava.gradle.util

fun <Type: Any> Type.isOneOf(vararg value: Type): Boolean =
    value.any(this::equals)