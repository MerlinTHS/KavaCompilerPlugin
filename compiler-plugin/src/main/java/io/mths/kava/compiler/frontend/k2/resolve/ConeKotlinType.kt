package io.mths.kava.compiler.frontend.k2.resolve

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.types.ConeKotlinType
import org.jetbrains.kotlin.fir.types.builder.buildResolvedTypeRef

/**
 * Replaces receivers typeRef property with a resolved reference of [coneType].
 */
context (FirSession)
infix fun <Type : FirExpression> Type.resolvedAs(
    coneType: ConeKotlinType
): Type = resolvedAs(
    buildResolvedTypeRef {
        type = coneType
    }
)