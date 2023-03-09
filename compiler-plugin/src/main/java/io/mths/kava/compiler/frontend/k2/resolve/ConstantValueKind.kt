package io.mths.kava.compiler.frontend.k2.resolve

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirConstExpression
import org.jetbrains.kotlin.fir.expressions.builder.buildConstExpression
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.expectedConeType
import org.jetbrains.kotlin.types.ConstantValueKind

/**
 * Creates a [FirConstExpression] from the receivers value and the constant [kind].
 */
context (FirSession)
infix fun <Type> Type.resolvedAs(
    kind: ConstantValueKind<Type>
): FirConstExpression<Type> =
    buildConstExpression(
        source = null,
        kind,
        value = this
    ) resolvedAs kind.expectedConeType(this@FirSession)