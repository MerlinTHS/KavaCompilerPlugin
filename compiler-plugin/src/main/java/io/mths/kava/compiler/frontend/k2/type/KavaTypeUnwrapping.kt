package io.mths.kava.compiler.frontend.k2.type

import io.mths.kava.util.whenNot
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.types.*

/**
 * In case [type] is an optional type [handled by Kava][isHandledByKava]
 * this function returns the non-optional type previously wrapped into [type].
 *
 * Otherwise, it returns the original [type] argument.
 */
context (FirSession)
fun unwrap(type: ConeKotlinType): ConeKotlinType =
    type whenNot { unwrapped }

/**
 * Checks if receiver is an optional type [handles by Kava][isHandledByKava] and calls corresponding
 * unwrap* method to convert it to a non-optional type in case it is.
 *
 * Returns null when it isn't or the conversion failed.
 *
 */
// TODO: Tightly couple conditions with the ones checked for [isHandledByKava].
context (FirSession)
internal val ConeKotlinType.unwrapped: ConeKotlinType? get() =
    when {
        canBeNull -> unwrapNullable(type)
        isJavaOptional -> unwrapSingleTypeParameter(type)
        else -> null
    }

/**
 * Converts [type] to a non-nullable type.
 *
 * If it's already non-nullable, [type] will be returned unchanged.
 */
context (FirSession)
fun unwrapNullable(type: ConeKotlinType): ConeKotlinType =
    type.withNullability(ConeNullability.NOT_NULL, typeContext)

/**
 * Converts [type] to its first (and only) type parameter.
 *
 * _java.util.Optional< String >_ becomes _String_
 *
 * Returns null if there isn't exactly one type parameter.
 */
fun unwrapSingleTypeParameter(type: ConeKotlinType): ConeKotlinType? =
    type.typeArguments.singleOrNull()?.type