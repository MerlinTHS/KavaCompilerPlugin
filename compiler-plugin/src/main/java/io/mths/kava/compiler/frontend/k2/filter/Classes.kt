package io.mths.kava.compiler.frontend.k2.filter

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.resolve.providers.getRegularClassSymbolByClassId
import org.jetbrains.kotlin.fir.resolve.providers.symbolProvider
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol
import org.jetbrains.kotlin.name.ClassId

context (FirSession)
fun ClassId.toRegularSymbol(): FirRegularClassSymbol? =
    symbolProvider.getRegularClassSymbolByClassId(this)