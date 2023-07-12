package io.mths.kava.compiler.backend.ir.wrapper

import io.mths.kava.compiler.irClassSymbolOf
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.types.*
import org.jetbrains.kotlin.ir.types.impl.makeTypeProjection
import org.jetbrains.kotlin.ir.util.render
import org.jetbrains.kotlin.types.StarProjectionImpl
import org.jetbrains.kotlin.types.Variance
import io.mths.kava.compiler.KavaClassId.Optional as OptionalClassId

/**
 * Wraps the type into an optional type.
 * If it's not already nullable or a primitive type,
 * it will be transformed into a nullable type for performance reasons.
 *
 * String -> String?
 *
 * Otherwise, the type is wrapped into a [java.util.Optional].
 *
 * Int -> Optional < Int >
 *
 * It doesn't matter if the original type is already optional!
 * It's always wrapped!
 */
context (IrPluginContext)
fun IrType.asWrappedType(
    variance: Variance = Variance.INVARIANT
): IrType =
    //if (canBeWrappedAsNullable()) {
        //makeNullable()
    //} else {
        wrapIntoOptional(variance)
    //}

internal fun IrType.canBeWrappedAsNullable(): Boolean =
    !(isNullable() and isPrimitiveType())

context (IrPluginContext)
internal fun IrType.wrapIntoOptional(
    variance: Variance
): IrType =
    irClassSymbolOf(OptionalClassId)
        .typeWithArguments(
            listOf(
                makeTypeProjection(this, variance),
            )
        )

context (IrPluginContext)
fun IrTypeArgument.wrappedIntoOptional(): IrSimpleType {
    val optional = irClassSymbolOf(OptionalClassId)

    return when (this) {
        is IrTypeProjection -> {
            optional.typeWithArguments(
                listOf(makeTypeProjection(type, variance))
            )
        }
        is IrStarProjection -> {
            optional.starProjectedType
        }
        else -> throw AssertionError("Unexpected IrTypeArgument: $this")
    }
}