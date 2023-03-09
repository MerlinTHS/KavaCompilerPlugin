package io.mths.kava.compiler.frontend.k2.type

import org.jetbrains.kotlin.fir.types.ConeKotlinType
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.types.coneType
import org.jetbrains.kotlin.fir.types.lowerBoundIfFlexible

/**
 * Returns the lowered cone type referenced by the result of [supplyTypeRef]
 * in case it's a flexible type.
 * Use this function when working with symbols from Java,
 * which use platform types (Unknown nullability) for their types.
 *
 * There's also a [discussion on slack](https://kotlinlang.slack.com/archives/C7L3JB43G/p1678115012276059) about it.
 *
 * **See:** [Notation for Platform Types](https://kotlinlang.org/docs/java-interop.html#notation-for-platform-types)
 */
inline infix fun <Type> Type.lowered(
    supplyTypeRef: Type.() -> FirTypeRef
): ConeKotlinType =
    supplyTypeRef()
        .coneType
        .lowerBoundIfFlexible()