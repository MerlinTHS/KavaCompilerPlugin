package io.mths.kava.compiler.frontend.k2.type

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mths.kava.compiler.frontend.k2.util.builtIn
import org.jetbrains.kotlin.fir.BuiltinTypes
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.plugin.createConeType
import org.jetbrains.kotlin.fir.resolve.providers.symbolProvider
import org.jetbrains.kotlin.fir.types.*
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.StandardClassIds
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class UnwrappingTest {
    private val builtIns = BuiltinTypes()
    private val session = mockk<FirSession>()
    private val context = mockk<ConeInferenceContext>()

    init {
        every { session.symbolProvider.getClassLikeSymbolByClassId(any()) } returns null
        every { session.builtinTypes } returns builtIns
        every { session.typeContext } returns context
    }

    private fun ConeKotlinType.wrappedIntoOptional(
        nullable: Boolean = false
    ): ConeKotlinType =
        ClassId.fromString("java/util/Optional")
            .createConeType(session, typeArguments = arrayOf(this), nullable)

    @Test
    fun `Java Optional String`() = with(session) {
        val nonOptionalString = builtIn { stringType }
        val optionalString = nonOptionalString.wrappedIntoOptional()

        assertEquals(
            expected = nonOptionalString,
            actual = unwrap(optionalString)
        )
    }

    @Test
    fun `Optional without generics remains the same`() = with(session) {
        val optionalWithoutGenerics = ClassId.fromString("java/util/Optional")
            .createConeType(session)

        assertEquals(
            expected = optionalWithoutGenerics,
            actual = unwrap(optionalWithoutGenerics)
        )
    }

    @Test
    fun `Nullable integer`() = with(session) {
        val nonNullableInt = builtIn { intType }
        val nullableInt = StandardClassIds.Int
            .createConeType(session, nullable = true)

        assertEquals(
            expected = nonNullableInt,
            actual = unwrap(nullableInt)
        )
    }

    @Test
    fun `Nullable Optional String`() = with(session) {
        val nonNullableOptional = builtIn { stringType }.wrappedIntoOptional()
        val nullableOptional = builtIn { stringType }.wrappedIntoOptional(nullable = true)

        assertEquals(
            expected = nonNullableOptional,
            actual = unwrap(nullableOptional)
        )
    }

    @Test
    fun `Non-Nullable remains the same`() = with(session) {
        val nonNullable = builtIn { stringType }

        assertEquals(
            expected = nonNullable,
            actual = unwrap(nonNullable)
        )
    }
}