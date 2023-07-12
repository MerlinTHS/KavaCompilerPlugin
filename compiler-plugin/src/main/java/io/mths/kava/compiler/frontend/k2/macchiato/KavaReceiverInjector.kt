package io.mths.kava.compiler.frontend.k2.macchiato

import io.mths.kava.compiler.frontend.k2.util.builtIn
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.*
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder
import org.jetbrains.kotlin.fir.extensions.FirExpressionResolutionExtension
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference
import org.jetbrains.kotlin.fir.renderWithType
import org.jetbrains.kotlin.fir.resolve.providers.symbolProvider
import org.jetbrains.kotlin.fir.symbols.SymbolInternals
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol
import org.jetbrains.kotlin.fir.types.ConeKotlinType
import org.jetbrains.kotlin.fir.types.builder.buildResolvedTypeRef
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName

class KavaReceiverInjector(
    session: FirSession
) : FirExpressionResolutionExtension(session) {
    private val alreadyHandled = mutableListOf<CallableId>()
    private var isFirst = true

    @OptIn(SymbolInternals::class)
    override fun addNewImplicitReceivers(
        functionCall: FirFunctionCall
    ): List<ConeKotlinType> = with(session) {
        // 1. Return type has to be optional, Unit or Nothing!
        // or has been annotated with @Kava

        if(functionCall.calleeReference.name.asString() == "useOptional") {
            val ref = functionCall.calleeReference
            if (ref is FirResolvedNamedReference) {
                val functionSymbol = ref.resolvedSymbol
                if (functionSymbol is FirNamedFunctionSymbol) {
                    if (functionSymbol.annotations.isNotEmpty()) {
                        println("Contains annotations")
                        session.symbolProvider.getClassLikeSymbolByClassId(
                            ClassId.topLevel(FqName(Exception::class.qualifiedName!!))
                        )

                        if (isFirst) {
                            isFirst = false

                            println("\tFirst time°!!")/*
                            functionSymbol.fir.replaceReturnTypeRef(
                                buildResolvedTypeRef {
                                    type = builtIn { intType }
                                }
                            )*/

                            functionSymbol.fir.body?.transformStatements(
                                data = null,
                                transformer = OptionalTypeUnwrapper(session)
                            )
                        }
                    }
                }
            }

            functionCall.replaceArgumentList(
                FirArgumentListBuilder().apply {
                    arguments.addAll(
                        functionCall.arguments.map { expression ->
                            println("Argument: ${expression.renderWithType()}")
                            if (expression is FirLambdaArgumentExpression) {
                                val function = expression.expression
                                if (function is FirAnonymousFunctionExpression) {
                                    val anonymous = function.anonymousFunction

                                    println(
                                        anonymous.renderWithType()
                                    )
                                }
                            }

                            expression
                        }
                    )
                }.build()
            )
        }

        return emptyList()
    }
}