package io.mths.kava.compiler.backend.ir.transform

import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrReturn
import org.jetbrains.kotlin.ir.util.render
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid

/**
 * @param[transform] The method invoked for every expression being returned.
 */
class ReturnedExpressionTransformer(
    private val transform: (IrExpression) -> IrExpression
) : IrElementTransformerVoid() {

    override fun visitReturn(expression: IrReturn) = expression.apply {
        value = transform(value)
    }
}