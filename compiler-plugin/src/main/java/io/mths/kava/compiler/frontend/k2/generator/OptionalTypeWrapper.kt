package io.mths.kava.compiler.frontend.k2.generator

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtension

/**
 * Collects functions annotated with [io.mths.kava.Kava] and
 * generates
 */
class OptionalTypeWrapper(
    session: FirSession
) : FirDeclarationGenerationExtension(session) {

}