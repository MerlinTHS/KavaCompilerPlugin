package io.mths.kava.compiler.frontend.k2.filter

import io.mths.kava.compiler.IllegalStates
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.resolve.ScopeSession
import org.jetbrains.kotlin.fir.scopes.getDeclaredConstructors
import org.jetbrains.kotlin.fir.scopes.unsubstitutedScope
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol
import org.jetbrains.kotlin.name.ClassId

/**
 * Filters the first constructor matching [predicate] or returns null.
 *
 * Use this method whenever you deal with Java classes.
 * For Kotlin classes there is no enhancement (from Java to Kotlin types) needed,
 * so it's enough to use FirClassSymbol.declaredMemberScope(FirSession).getDeclaredConstructors()...
 *
 * There's also a [discussion on slack](https://kotlinlang.slack.com/archives/C7L3JB43G/p1678115012276059) about it.
 */
context (FirSession)
fun FirClassSymbol<*>.searchConstructor(
    predicate: FirConstructorSymbol.() -> Boolean
): FirConstructorSymbol? =
    unsubstitutedScope(this@FirSession, ScopeSession(), true)
        .getDeclaredConstructors()
        .firstOrNull(predicate)

/**
 * Finds the first constructor matching [predicate] or throws an IllegalStateException.
 *
 * Use this method only when you know, that what you're searching for have to be there,
 * so it would really be an illegal state!
 *
 * See [searchConstructor] for a relaxed alternative.
 */
context (FirSession)
fun ClassId.findConstructor(
    predicate: FirConstructorSymbol.() -> Boolean
): FirConstructorSymbol =
    toRegularSymbol()
        ?.searchConstructor(predicate)
        ?: throw IllegalStates.noConstructorFor(this)