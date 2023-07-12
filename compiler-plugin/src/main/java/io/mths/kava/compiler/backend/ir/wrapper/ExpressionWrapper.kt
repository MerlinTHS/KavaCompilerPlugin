package io.mths.kava.compiler.backend.ir.wrapper

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.util.functions
import org.jetbrains.kotlin.ir.util.render
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.Name
import io.mths.kava.compiler.KavaClassId.Optional as OptionalClassId

context (IrPluginContext)
fun IrExpression.asWrappedExpression(): IrExpression {
    val optionalOfFunction = referenceStaticFunction(OptionalClassId, "of")
        ?: throw IllegalStateException("No 'of' function found in java.util.Optional!")

    val emptyFunction = referenceStaticFunction(OptionalClassId, "empty")
        ?: throw IllegalStateException("No 'empty' function found in java.util.Optional!")

    return with(DeclarationIrBuilder(this@IrPluginContext, optionalOfFunction.symbol)) {
        irCall(optionalOfFunction).apply {
            putValueArgument(0, this@asWrappedExpression)
        }
    }
}

fun IrPluginContext.referenceStaticFunction(
    classId: ClassId,
    name: String
): IrSimpleFunction? {
    val identifier = Name.identifier(name)

    return referenceClass(classId)
        ?.owner
        ?.functions
        ?.first { it.name == identifier }
}