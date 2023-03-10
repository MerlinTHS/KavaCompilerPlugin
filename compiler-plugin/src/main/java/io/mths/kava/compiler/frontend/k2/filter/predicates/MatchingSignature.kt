package io.mths.kava.compiler.frontend.k2.filter.predicates

import io.mths.kava.compiler.frontend.k2.type.lowered
import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol
import org.jetbrains.kotlin.fir.types.ConeKotlinType
import org.jetbrains.kotlin.fir.types.coneType
import org.jetbrains.kotlin.fir.types.lowerBoundIfFlexible

/**
 * Checks whether the function can be called using the supplied
 * [arguments].
 *
 * It's a convenient version of [canBeCalledWith] for argumentTypes.
 */
fun <Type : FirFunction> FirFunctionSymbol<Type>.canBeCalledWith(
    vararg arguments: FirExpression
): Boolean = canBeCalledWith(
    *arguments
        .map { it.typeRef.coneType }
        .toTypedArray()
)

/**
 * Checks whether the function can be called using the supplied
 * [argumentTypes].
 *
 * It compares the length and iterates through the parameters and
 * arguments to check if the types are equal.
 * Flexible types are lowered.
 */
fun <Type : FirFunction> FirFunctionSymbol<Type>.canBeCalledWith(
    vararg argumentTypes: ConeKotlinType
): Boolean = with(valueParameterSymbols) {
    (size == argumentTypes.size) && matchesLowered(*argumentTypes)
}

/**
 * Compares every parameter from the receivers list with the
 * argument at the same index from the [argumentTypes] list.
 * Flexible types are lowered.
 *
 * If the collections have different lengths, the shortest length will be
 * used for comparison.
 */
fun List<FirValueParameterSymbol>.matchesLowered(
    vararg argumentTypes: ConeKotlinType
): Boolean =
    zip(argumentTypes).all { (parameter, mayFlexibleArgumentType) ->
        val parameterType = parameter lowered { resolvedReturnTypeRef }
        val argumentType = mayFlexibleArgumentType.lowerBoundIfFlexible()

        parameterType == argumentType
    }