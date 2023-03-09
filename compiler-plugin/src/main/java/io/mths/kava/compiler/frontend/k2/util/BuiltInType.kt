package io.mths.kava.compiler.frontend.k2.util

import org.jetbrains.kotlin.fir.BuiltinTypes
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.types.ConeKotlinType
import org.jetbrains.kotlin.fir.types.coneType
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef

context (FirSession)
fun builtIn(
    supplyRef: BuiltinTypes.() -> FirImplicitBuiltinTypeRef
): ConeKotlinType =
    builtinTypes.supplyRef().coneType