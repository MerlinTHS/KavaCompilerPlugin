package io.mths.kava.compiler.frontend.k2.type

import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol
import org.jetbrains.kotlin.fir.types.ConeKotlinType
import org.jetbrains.kotlin.fir.types.lowerBoundIfFlexible

/**
 * Returns the lowered resolvedReturnType in case the callable symbol is of a flexible type.
 * Use this property when working with callable symbols from Java,
 * which use platform types (Unknown nullability) for their types.
 *
 * There's also a [discussion on slack](https://kotlinlang.slack.com/archives/C7L3JB43G/p1678115012276059) about it.
 *
 * **See:** [Notation for Platform Types](https://kotlinlang.org/docs/java-interop.html#notation-for-platform-types)
 */
val <Type> FirCallableSymbol<Type>.loweredReturnType: ConeKotlinType
where Type : FirCallableDeclaration
    get() = resolvedReturnType.lowerBoundIfFlexible()