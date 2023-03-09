package io.mths.kava.compiler.frontend.k2.builder

import io.mths.kava.compiler.frontend.k2.resolve.resolvedAs
import io.mths.kava.compiler.frontend.k2.util.builtIn
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.expressions.FirOperation
import org.jetbrains.kotlin.fir.expressions.buildBinaryArgumentList
import org.jetbrains.kotlin.fir.expressions.builder.buildEqualityOperatorCall

/**
 * Builds a resolved equality operator call which compares
 * [left] and [right] using the equals (==) operator.
 */
context (FirSession)
fun resolvedEquals(
    left: FirExpression,
    right: FirExpression
): FirEqualityOperatorCall =
    buildEqualityOperatorCall {
        operation = FirOperation.EQ
        argumentList = buildBinaryArgumentList(left, right)
    } resolvedAs (builtIn { booleanType })