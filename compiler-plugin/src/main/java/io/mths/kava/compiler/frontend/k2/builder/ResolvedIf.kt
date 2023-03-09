package io.mths.kava.compiler.frontend.k2.builder

import io.mths.kava.compiler.frontend.k2.resolve.resolvedAs
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.expressions.builder.*
import org.jetbrains.kotlin.fir.types.FirTypeRef

/**
 * Infers typeRef from [thenBranch].
 */
context (FirSession)
fun resolvedIf(
    condition: FirExpression,
    thenBranch: FirExpression,
    elseBranch: FirExpression,
    typeRef: FirTypeRef = thenBranch.typeRef
) = buildWhenExpression {
    usedAsExpression = true

    branches += listOf(
        resolvedThenBranch(condition, thenBranch),
        resolvedElseBranch(elseBranch)
    )
} resolvedAs typeRef

context (FirSession)
internal fun resolvedThenBranch(
    condition: FirExpression,
    expression: FirExpression
) = buildWhenBranch {
    this.condition = condition

    setResolvedResult(expression)
}

context (FirSession)
internal fun resolvedElseBranch(
    expression: FirExpression
) = buildWhenBranch {
    condition = buildElseIfTrueCondition()
        .resolvedAs(builtinTypes.booleanType)

    setResolvedResult(expression)
}