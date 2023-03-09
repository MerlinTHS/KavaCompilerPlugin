package io.mths.kava.compiler.frontend.k2.filter

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.resolve.ScopeSession
import org.jetbrains.kotlin.fir.scopes.getDeclaredConstructors
import org.jetbrains.kotlin.fir.scopes.unsubstitutedScope
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol

/**
 * Filters the first constructor matching [predicate].
 *
 * Use this method whenever you deal with Java classes.
 * For Kotlin classes there is no enhancement (from Java to Kotlin types) needed,
 * so it's enough to use FirClassSymbol.declaredMemberScope(FirSession).getDeclaredConstructors()...
 *
 * There's also a [discussion on slack](https://kotlinlang.slack.com/archives/C7L3JB43G/p1678115012276059) about it.
 */
context (FirSession)
fun FirClassSymbol<*>.findConstructor(
    predicate: FirConstructorSymbol.() -> Boolean
): FirConstructorSymbol? =
    unsubstitutedScope(this@FirSession, ScopeSession(), true)
        .getDeclaredConstructors()
        .firstOrNull(predicate)