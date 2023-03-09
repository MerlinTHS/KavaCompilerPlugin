package io.mths.kava.compiler.frontend.k2.builder

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirArgumentList
import org.jetbrains.kotlin.fir.expressions.builder.buildThrowExpression
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol

/**
 * Builds an expression which throws the exception built with [constructor]
 * and [arguments].
 *
 *
 */
context (FirSession)
fun resolvedThrow(
    constructor: FirConstructorSymbol,
    arguments: FirArgumentList
) = buildThrowExpression {
    exception = constructor calledWith arguments
}