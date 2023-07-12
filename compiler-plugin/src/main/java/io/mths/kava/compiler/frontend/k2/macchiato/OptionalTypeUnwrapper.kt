package io.mths.kava.compiler.frontend.k2.macchiato

import io.mths.kava.compiler.frontend.k2.builder.resolvedEquals
import io.mths.kava.compiler.frontend.k2.builder.resolvedExceptionThrow
import io.mths.kava.compiler.frontend.k2.builder.resolvedIf
import io.mths.kava.compiler.frontend.k2.resolve.resolvedAs
import io.mths.kava.compiler.frontend.k2.resolve.asConstant
import io.mths.kava.compiler.frontend.k2.type.isHandledByKava
import io.mths.kava.compiler.frontend.k2.util.builtIn
import io.mths.kava.util.whenNot
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.*
import org.jetbrains.kotlin.fir.expressions.builder.*
import org.jetbrains.kotlin.fir.renderWithType
import org.jetbrains.kotlin.fir.types.*
import org.jetbrains.kotlin.fir.visitors.FirTransformer
import org.jetbrains.kotlin.types.ConstantValueKind
import io.mths.kava.compiler.frontend.k2.type.unwrap

class OptionalTypeUnwrapper(
    private val session: FirSession
) : FirTransformer<Nothing?>() {

    override fun transformFunctionCall(
        functionCall: FirFunctionCall,
        data: Nothing?
    ): FirStatement = with(session) {
        functionCall whenNot { wrappedIntoTypeCheck }
    }


    override fun transformReturnExpression(
        returnExpression: FirReturnExpression,
        data: Nothing?
    ): FirStatement = with(session) {
        println("Return Expression is ${returnExpression.renderWithType()}")
        return returnExpression whenNot {
            (result as? FirFunctionCall)?.wrappedIntoTypeCheck
        }
    }

    override fun <E : FirElement> transformElement(
        element: E,
        data: Nothing?
    ): E = element
}

/**
 * If the function calls return type is [handled by Kava][isHandledByKava],
 * it returns the call wrapped into a type check for leaving the current Kava context
 * or null when the return type isn't handled by Kava.
 */
context (FirSession)
internal val FirFunctionCall.wrappedIntoTypeCheck: FirExpression?
    get() = with(typeRef.coneType) {
        println("Wrapping ${renderWithType()}")
        when {
            canBeNull -> wrapIntoNullCheck()
            //isJavaOptional -> wrapIntoJavaOptionalCheck()
            else -> null
        }
    }

/**
 * Wraps the function call into an elvis-operator
 * which throws an exception when the result is null.
 *
 * Use this function when the return type is nullable.
 */
context (FirSession)
internal fun FirFunctionCall.wrapIntoNullCheck(): FirElvisExpression =
    buildElvisExpression {
        lhs = this@wrapIntoNullCheck
        rhs = resolvedExceptionThrow("Value is nullable!")
    } resolvedAs unwrap(typeRef.coneType)

/**
 * Wraps the function call into an if-expression
 * which throws an exception when the result is empty.
 *
 * Use this function when the return type is [java.util.Optional].
 */
context (FirSession)
internal fun FirFunctionCall.wrapIntoJavaOptionalCheck(): FirExpression =
    resolvedIf( // call isEmpty!
        condition = resolvedEquals(this, null asConstant ConstantValueKind.Null),
        thenBranch = this,
        elseBranch = resolvedExceptionThrow("Optional value is empty!")
    )