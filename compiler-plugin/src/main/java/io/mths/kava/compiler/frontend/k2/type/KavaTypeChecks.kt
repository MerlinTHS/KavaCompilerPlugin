package io.mths.kava.compiler.frontend.k2.type

import org.jetbrains.kotlin.fir.types.ConeKotlinType
import org.jetbrains.kotlin.fir.types.canBeNull
import org.jetbrains.kotlin.fir.types.classId
import org.jetbrains.kotlin.name.ClassId

/**
 * Checks whether the receiver references a Kava Optional Type.
 *
 * Some examples are:
 * - Nullable types
 * - java.lang.Optional<T>
 */
val ConeKotlinType.isHandledByKava: Boolean get() =
    canBeNull or isJavaOptional

/**
 * Checks whether the receiver is of type [java.util.Optional] by
 * comparing classIds.
 */
val ConeKotlinType.isJavaOptional: Boolean get() =
    classId == ClassId.fromString("java/util/Optional")