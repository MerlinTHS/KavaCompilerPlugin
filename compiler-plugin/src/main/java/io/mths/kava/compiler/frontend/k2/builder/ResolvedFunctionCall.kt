package io.mths.kava.compiler.frontend.k2.builder

import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.expressions.FirArgumentList
import org.jetbrains.kotlin.fir.expressions.builder.buildFunctionCall
import org.jetbrains.kotlin.fir.references.builder.buildResolvedNamedReference
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol

infix fun <Type : FirFunction> FirFunctionSymbol<Type>.calledWith(
    arguments: FirArgumentList
) = buildFunctionCall {
    typeRef = this@calledWith.resolvedReturnTypeRef
    argumentList = arguments
    calleeReference = buildResolvedNamedReference {
        name = this@calledWith.name
        resolvedSymbol = this@calledWith
    }
}