package io.mths.kava.compiler.frontend.k2

import io.mths.kava.compiler.frontend.k2.macchiato.KavaReceiverInjector
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.fir.SessionConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter

class KavaFirRegistrar : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        + (::TopLevelDeclarationGenerator)
        + (::KavaReceiverInjector)
    }
}

fun CompilerPluginRegistrar.ExtensionStorage.registerK2Frontend() {
    FirExtensionRegistrarAdapter
        .registerExtension(KavaFirRegistrar())
}