package io.mths.kava.compiler.frontend.k2.resolve

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.plugin.createConeType
import org.jetbrains.kotlin.fir.types.ConeTypeProjection
import org.jetbrains.kotlin.name.ClassId

/**
 * Replaces receivers typeRef with a resolved one based
 * on [id].
 *
 * Use this infix shorthand variant whenever:
 * - there are no generic type arguments
 * - the type is non-nullable
 */
context (FirSession)
infix fun <Type : FirExpression> Type.resolvedAs(
    id: ClassId
): Type = resolvedAs(id, typeArguments = emptyArray())

/**
 * Replaces receivers typeRef with a resolved one based
 * on [id], its [typeArguments] and nullability.
 *
 * Use this variant when the type is nullable or has
 * at least one generic type argument.
 *
 * Otherwise, consider using the infix variant of this function.
 */
context (FirSession)
fun <Type : FirExpression> Type.resolvedAs(
    id: ClassId,
    typeArguments: Array<ConeTypeProjection>,
    nullable: Boolean = false
): Type = resolvedAs(
    coneType = id.createConeType(this@FirSession, typeArguments, nullable)
)