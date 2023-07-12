package io.mths.kava.compiler.frontend.k2

import io.mths.kava.compiler.IllegalState
import io.mths.kava.compiler.frontend.k2.type.isHandledByKava
import io.mths.kava.compiler.frontend.k2.type.unwrap
import io.mths.kava.util.withEach
import org.jetbrains.kotlin.GeneratedDeclarationKey
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.declarations.utils.modality
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtension
import org.jetbrains.kotlin.fir.extensions.FirDeclarationPredicateRegistrar
import org.jetbrains.kotlin.fir.extensions.MemberGenerationContext
import org.jetbrains.kotlin.fir.extensions.predicate.LookupPredicate
import org.jetbrains.kotlin.fir.extensions.predicateBasedProvider
import org.jetbrains.kotlin.fir.plugin.SimpleFunctionBuildingContext
import org.jetbrains.kotlin.fir.plugin.createConeType
import org.jetbrains.kotlin.fir.plugin.createTopLevelFunction
import org.jetbrains.kotlin.fir.renderWithType
import org.jetbrains.kotlin.fir.symbols.SymbolInternals
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol
import org.jetbrains.kotlin.fir.types.*
import org.jetbrains.kotlin.name.CallableId
import io.mths.kava.compiler.KavaClassId.KavaContext as KavaContextId
import io.mths.kava.compiler.KavaClassId.KavaAnnotation as KavaAnnotationId

class TopLevelDeclarationGenerator(
    session: FirSession
) : FirDeclarationGenerationExtension(session) {
    companion object {
        private val PREDICATE = LookupPredicate.create {
            annotated(KavaAnnotationId.asSingleFqName())
        }
    }

    object Key : GeneratedDeclarationKey()

    private val provider = session.predicateBasedProvider
    private val functionById: MutableMap<CallableId, FirNamedFunctionSymbol> = HashMap()

    /**
     * Statements in a Kava context.
     *
     * Currently only named functions annotated with @Kava.
     */
    private val matchedFunctions by lazy {
        provider
            .getSymbolsByPredicate(PREDICATE)
            .filterIsInstance<FirNamedFunctionSymbol>() // TODO: Include contextual lambdas
    }

    // TODO: Filter @Kava annotation
    // TODO: Add type arguments to KavaContext<T>
    @OptIn(SymbolInternals::class)
    override fun generateFunctions(
        callableId: CallableId,
        context: MemberGenerationContext?
    ) = buildList { with(session) {
        if (context == null) {
            val contextType = KavaContextId.createConeType(session)
            val original = originalOf(callableId)

            println("Found original: ${original.fir.renderWithType()}")
            val generatedFunction = createTopLevelFunction(Key, callableId, unwrap(original.resolvedReturnType)) {
                contextReceiver(contextType)
                typeParametersFrom(original.typeParameterSymbols)
                original.modality?.let(::modality::set)
            }.apply {
                replaceValueParameters(original.valueParameterSymbols.map { it.fir })
                replaceAnnotations(original.annotations)
                replaceContextReceivers(original.resolvedContextReceivers + contextReceivers)
            }

            println("Generated is: ${generatedFunction.symbol.fir.renderWithType()}")
            add(generatedFunction.symbol)
        }}
    }

    override fun getTopLevelCallableIds(): Set<CallableId> =
        matchedFunctions
            .filter { it.resolvedReturnType.isHandledByKava }
            .mapTo(mutableSetOf()) { function ->
                function.callableId.also {
                    functionById[it] = function
                }
            }

    override fun FirDeclarationPredicateRegistrar.registerPredicates() {
        register(PREDICATE)
    }

    private fun originalOf(
        callableId: CallableId
    ) = functionById[callableId]
        ?: throw IllegalState.noCallableSymbolFor(callableId)
}

fun SimpleFunctionBuildingContext.typeParametersFrom(
    symbols: Iterable<FirTypeParameterSymbol>
) {
    symbols.withEach {
        typeParameter(name, variance, isReified, TopLevelDeclarationGenerator.Key)
    }
}