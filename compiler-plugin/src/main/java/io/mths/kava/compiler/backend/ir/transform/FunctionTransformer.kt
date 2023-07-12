package io.mths.kava.compiler.backend.ir.transform

import io.mths.kava.compiler.backend.ir.wrapper.wrappedIntoOptional
import io.mths.kava.util.withEach
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.interpreter.hasAnnotation
import org.jetbrains.kotlin.ir.types.*
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer
import org.jetbrains.kotlin.ir.visitors.transformChildrenVoid
import org.jetbrains.kotlin.name.FqName

/**
 * The transformer allows to modify every
 * - return type using [transformType]
 * - returned expression using [transformExpression]
 *
 * of function declarations [interestedIn].
 */
class FunctionTransformer(
    private val context: IrPluginContext,
    private val interestedIn: IrAnnotationContainer.() -> Boolean,
    private val transformType: IrType.() -> IrType,
    private val transformExpression: IrExpression.() -> IrExpression
) : IrElementTransformer<MutableSet<IrFunction>> {

    override fun visitFunctionExpression(
        expression: IrFunctionExpression,
        data: MutableSet<IrFunction>
    ): IrElement = with (context) {
        if (interestedIn(expression.type)) {
            transform(expression.function).also {
                data.add(it)
            }

            expression.apply {
                type = type.transformWhenAnnotated()
            }
        }

        return super.visitFunctionExpression(expression, data)
    }

    override fun visitCall(
        expression: IrCall,
        data: MutableSet<IrFunction>
    ): IrElement {
        val allParameters = expression.symbol.owner.valueParameters
        val annotatedParameters = allParameters.filter {
            interestedIn(it.type)
        }

        for (parameter in annotatedParameters) {
            val index = allParameters.indexOf(parameter)
            val argument = expression.getValueArgument(index)
                ?: throw IllegalStateException("No argument found for parameter ${parameter.name.asString()} at index $index!")

            val annotations = parameter.type.annotations
            argument.type = argument.type.addAnnotations(annotations)
        }

        return super.visitCall(expression, data)
    }

    override fun visitFunction(
        declaration: IrFunction,
        data: MutableSet<IrFunction>
    ): IrStatement = with (context) {
        if (interestedIn(declaration)) {
            transform(declaration).also {
                data.add(it)
            }
        } else {
            declaration.valueParameters.withEach {
                type = type.transformWhenAnnotated()
            }
            super.visitFunction(declaration, data)
        }
    }

    /**
     * Transforms the given function declaration to a declaration with optional types.
     * If the original return type is String, it will be converted to Optional < String >.
     *
     * The return type and every return statement is changed accordingly.
     * Unit functions first get an additional return statement to fit the rest.
     */
    context (IrPluginContext)
    private fun transform(function: IrFunction) = function.apply {
        if (returnType.isUnit()) {
            function.addUnitReturn()
        }
        returnType = transformType(returnType)

        transformChildrenVoid(
            ReturnedExpressionTransformer(transformExpression)
        )
    }

    context (IrPluginContext)
    fun IrType.transformWhenAnnotated(): IrType {
        if (interestedIn()) {
            return (this as IrSimpleType).withLastTypeArgument {
                wrappedIntoOptional()
            }
        }

        return this
    }
}

/**
 * Transforms all functions of the [IrModuleFragment] for which [interestedIn] return true.
 * The return type is modified by [transformType], all returned expressions by [transformExpression].
 *
 * Returns the set of all transformed functions.
 */
context (IrPluginContext)
fun IrModuleFragment.transformFunctions(
    interestedIn: IrAnnotationContainer.() -> Boolean,
    transformType: IrType.() -> IrType,
    transformExpression: IrExpression.() -> IrExpression
) = buildSet<IrFunction> {
    transformChildren(
        FunctionTransformer(this@IrPluginContext, interestedIn, transformType, transformExpression),
        data = this@buildSet
    )
}