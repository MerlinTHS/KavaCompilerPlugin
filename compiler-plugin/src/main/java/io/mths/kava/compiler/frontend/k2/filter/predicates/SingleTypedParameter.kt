package io.mths.kava.compiler.frontend.k2.filter.predicates

import io.mths.kava.compiler.frontend.k2.type.loweredReturnType
import io.mths.kava.compiler.frontend.k2.util.builtIn
import org.jetbrains.kotlin.fir.BuiltinTypes
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef

/**
 * Checks whether the function takes only one argument of the supplied builtin type.
 *
 * You can use it for Kotlin as well as for Java functions, because the parameter types are lowered.
 */
context (FirSession)
fun <Type : FirFunction> FirFunctionSymbol<Type>.takesSingle(
    builtinType: BuiltinTypes.() -> FirImplicitBuiltinTypeRef
): Boolean = with(valueParameterSymbols) {
    (size == 1) && first().loweredReturnType == builtIn(builtinType)
}