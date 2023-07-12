package io.mths.kava.compiler.backend.ir.transform

import io.mths.kava.util.replaceLast
import org.jetbrains.kotlin.ir.types.*
import org.jetbrains.kotlin.ir.types.impl.buildSimpleType

/**
 * Builds an [IrSimpleType] with the last type argument
 * modified by [transform].
 *
 * @throws[NoSuchElementException] If there is no type
 * argument.
 */
fun IrSimpleType.withLastTypeArgument(
    transform: IrTypeArgument.() -> IrTypeArgument
) = buildSimpleType {
    val original = arguments.last()
    val transformed = transform(original)

    arguments = arguments.replaceLast(transformed)
}