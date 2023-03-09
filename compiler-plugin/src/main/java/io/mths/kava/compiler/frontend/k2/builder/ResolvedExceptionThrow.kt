package io.mths.kava.compiler.frontend.k2.builder

import io.mths.kava.compiler.frontend.k2.filter.findConstructor
import io.mths.kava.compiler.frontend.k2.filter.predicates.takesSingle
import io.mths.kava.compiler.frontend.k2.resolve.asConstant
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirThrowExpression
import org.jetbrains.kotlin.fir.expressions.buildUnaryArgumentList
import org.jetbrains.kotlin.types.ConstantValueKind
import io.mths.kava.compiler.KavaClassIds.Exception as ExceptionId

/**
 * Returns an expression which throws an [java.lang.Exception]
 * with the supplied [message].
 */
context (FirSession)
fun resolvedExceptionThrow(
    message: String
): FirThrowExpression = resolvedThrow(
    constructor = ExceptionId.findConstructor { takesSingle { stringType } },
    arguments = buildUnaryArgumentList(
        message asConstant ConstantValueKind.String
    )
)