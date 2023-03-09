package io.mths.kava.compiler.frontend.k2.builder

import io.mths.kava.compiler.frontend.k2.resolve.resolvedAs
import io.mths.kava.compiler.frontend.k2.util.builtIn
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.expressions.FirOperation
import org.jetbrains.kotlin.fir.expressions.builder.buildArgumentList
import org.jetbrains.kotlin.fir.expressions.builder.buildEqualityOperatorCall

/**
 * Builds a resolved equality operator call which compares
 * [leftOperand] and [rightOperand] using the equals (==) operator.
 */
context (FirSession)
fun buildResolvedEquals(
    leftOperand: FirExpression,
    rightOperand: FirExpression
): FirEqualityOperatorCall =
    buildEqualityOperatorCall {
        operation = FirOperation.EQ
        argumentList = buildArgumentList {
            arguments += listOf(leftOperand, rightOperand)
        }
    } resolvedAs (builtIn { booleanType })