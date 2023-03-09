package io.mths.kava.compiler.frontend.k2.resolve

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.types.FirTypeRef

/**
 * Replaces receivers typeRef with [typeRef] and
 * returns the receiver.
 */
context (FirSession)
infix fun <Type : FirExpression> Type.resolvedAs(
    typeRef: FirTypeRef
): Type = apply {
    replaceTypeRef(typeRef)
}