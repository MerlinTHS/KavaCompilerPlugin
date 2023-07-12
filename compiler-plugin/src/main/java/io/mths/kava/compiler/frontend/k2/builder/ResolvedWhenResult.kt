package io.mths.kava.compiler.frontend.k2.builder

import io.mths.kava.compiler.frontend.k2.resolve.resolvedAs
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.expressions.builder.FirWhenBranchBuilder
import org.jetbrains.kotlin.fir.expressions.builder.buildBlock

context (FirSession)
fun FirWhenBranchBuilder.setResolvedResult(
    expression: FirExpression
) {
    source = expression.source
    result = buildBlock {
        statements += expression
    } resolvedAs expression.typeRef
}