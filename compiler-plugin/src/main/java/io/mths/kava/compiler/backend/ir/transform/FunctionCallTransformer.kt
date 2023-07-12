package io.mths.kava.compiler.backend.ir.transform

import io.mths.kava.compiler.KavaClassId
import io.mths.kava.compiler.backend.ir.wrapper.asWrappedType
import io.mths.kava.compiler.backend.ir.wrapper.referenceStaticFunction
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.builders.*
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol
import org.jetbrains.kotlin.ir.symbols.IrSymbol
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.ir.visitors.transformChildrenVoid
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.Name

/**
 * Wraps function calls into a check.
 * If the returned value represents a non-existing result ( null, Optional.Empty, ... )
 * the current function is left with a generated return statement.
 *
 * Otherwise, the existing (unwrapped) result is returned like the caller expects it.
 *
 * @param[functions] The functions previously being transformed in the backend,
 * that needs to be handled now at the call-site.
 */
class FunctionCallTransformer(
    private val context: IrPluginContext,
    private val functions: Set<IrFunction>
) : IrElementTransformerVoid() {
    private val functionSymbols by lazy {
        functions.map { it.symbol }
    }

    // TODO: Replace it with the data passed through the visitor
    private lateinit var currentFunction: IrFunction

    /**
     * Updates the function in which calls occur.
     */
    override fun visitFunction(declaration: IrFunction): IrStatement {
        currentFunction = declaration

        return super.visitFunction(declaration)
    }

    /**
     * TODO: Refactor
     */
    override fun visitCall(
        call: IrCall
    ): IrExpression = with(context) {
        if (call.symbol in functionSymbols) {
            val emptyFunction = optionalEmpty()
            val getFunction = optionalGet()
            wrapTypeOf(call)

            val myReturn = buildDeclaration(currentFunction.symbol) {
                irReturn(irCall(emptyFunction))
            }

            if (call.callsKavaFail()) {
                myReturn
            } else {
                buildDeclaration(call.symbol) {
                    irIfThenElse(
                        type = call.type,
                        condition = irNotEquals(call, irCall(emptyFunction)),
                        thenPart = irCall(getFunction).apply {
                            this.dispatchReceiver = call
                        },
                        elsePart = myReturn
                    )
                }
            }
        } else {
            super.visitCall(call)
        }
    }

    private fun <R> IrGeneratorContext.buildDeclaration(
        symbol: IrSymbol,
        build: DeclarationIrBuilder.() -> R
    ): R {
        val builder = DeclarationIrBuilder(this, symbol)
        val declaration = builder.build()

        return declaration
    }

    /**
     * Checks whether the given [IrCall] calls the Kava-builtin fail() function.
     */
    private fun IrCall.callsKavaFail(): Boolean {
        val owner = symbol.owner
        val name = owner.name.asString()

        return name == "fail"
    }

    context (IrPluginContext)
    private fun wrapTypeOf(expression: IrExpression) {
        val plainType = expression.type
        val wrappedType = plainType.asWrappedType()

        expression.type = wrappedType
    }

    private fun optionalGet(): IrSimpleFunctionSymbol {
        val classId = KavaClassId.Optional
        val callableId = CallableId(classId, Name.identifier("get"))
        val getFunction = context.referenceFunctions(callableId).firstOrNull()

        return getFunction
            ?: throw IllegalStateException("No 'get' function found in java.util.Optional!")
    }

    private fun optionalEmpty(): IrSimpleFunction {
        val classId = KavaClassId.Optional
        val emptyFunction = context.referenceStaticFunction(classId, "empty")

        return emptyFunction
            ?: throw IllegalStateException("No 'empty' function found in java.util.Optional!")
    }
}

context (IrPluginContext)
fun IrModuleFragment.transformFunctionCalls(functions: Set<IrFunction>) {
    transformChildrenVoid(
        FunctionCallTransformer(this@IrPluginContext, functions)
    )
}