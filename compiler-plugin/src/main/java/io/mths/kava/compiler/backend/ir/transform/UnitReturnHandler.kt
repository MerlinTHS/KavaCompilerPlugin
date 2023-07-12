package io.mths.kava.compiler.backend.ir.transform

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.ir.builders.irBlockBody
import org.jetbrains.kotlin.ir.builders.irReturnUnit
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.expressions.IrBody
import org.jetbrains.kotlin.ir.expressions.IrReturn
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol
import org.jetbrains.kotlin.ir.util.statements
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.ir.visitors.transformChildrenVoid

/**
 * Visits every body and adds 'return Unit' as the last statement.
 *
 * This is necessary because Unit functions normally don't contain
 * any return statement, whereas the following Kava transformations
 * expect at least one return statement, to wrap its value into an
 * optional type.
 *
 * It's intended to use the transformer only for the function you pass
 * as [returnTarget]. Transforming multiple bodies by adding return statements
 * referencing the same target (non-local) will result in exceptions like:
 *
 * **java.lang.NoClassDefFoundError: $$$$$NON_LOCAL_RETURN$$$$$**
 */
class UnitReturnAdder(
    private val context: IrPluginContext,
    private val returnTarget: IrReturnTargetSymbol
) : IrElementTransformerVoid() {

    /**
     * If the last statement in [body] is already a return statement,
     * there will be no modification.
     */
    override fun visitBody(body: IrBody): IrBody =
        if (body.statements.lastOrNull() is IrReturn) {
            super.visitBody(body)
        } else {
            body.withUnitReturn()
        }

    /**
     * Copies the statements of the receiver into a new block body and adds
     * a 'return Unit' as the last statement.
     */
    private fun IrBody.withUnitReturn() = buildIr {
        irBlockBody {
            for (statement in statements) {
                + statement
            }

            + irReturnUnit()
        }
    }

    /**
     * IR builder in the scope of [returnTarget],
     * which is important for the return statements.
     */
    private inline fun <Type> buildIr(
        configure: DeclarationIrBuilder.() -> Type
    ): Type {
        val builder = DeclarationIrBuilder(context, returnTarget)

        return builder.configure()
    }
}

/**
 * Adds a 'return Unit' as the last statement to the functions body,
 * if it doesn't already contain one.
 *
 * See [UnitReturnAdder]
 */
context (IrPluginContext)
fun IrFunction.addUnitReturn() {
    transformChildrenVoid(
        UnitReturnAdder(this@IrPluginContext, symbol)
    )
}