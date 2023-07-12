package io.mths.kava.compiler.backend.ir

import io.mths.kava.compiler.KavaClassId
import io.mths.kava.compiler.backend.ir.transform.transformFunctionCalls
import io.mths.kava.compiler.backend.ir.transform.transformFunctions
import io.mths.kava.compiler.backend.ir.wrapper.asWrappedExpression
import io.mths.kava.compiler.backend.ir.wrapper.asWrappedType
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.util.hasAnnotation

/**
 * Modifies function declarations annotated with @Kava to return an optional type.
 */
class KavaTransformer : IrGenerationExtension {
    override fun generate(
        moduleFragment: IrModuleFragment,
        pluginContext: IrPluginContext
    ) = with(pluginContext) {
        val transformedFunctions = moduleFragment.transformFunctionDeclarations()

        moduleFragment.transformFunctionCalls(transformedFunctions)
    }

    context (IrPluginContext)
    private fun IrModuleFragment.transformFunctionDeclarations(): Set<IrFunction> {
        val kava = KavaClassId.KavaAnnotation.asSingleFqName()

        return transformFunctions(
            interestedIn = { hasAnnotation(kava) },
            transformType = { asWrappedType() },
            transformExpression = { asWrappedExpression() }
        )
    }
}