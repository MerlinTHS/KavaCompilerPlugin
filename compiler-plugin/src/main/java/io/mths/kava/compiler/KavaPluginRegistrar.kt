package io.mths.kava.compiler

import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar

import com.google.auto.service.AutoService
import io.mths.kava.compiler.backend.registerBackend
import org.jetbrains.kotlin.config.CompilerConfiguration
import io.mths.kava.compiler.frontend.k2.registerK2Frontend
import io.mths.kava.compiler.frontend.old.registerOldFrontend

@AutoService(CompilerPluginRegistrar::class)
class KavaPluginRegistrar : CompilerPluginRegistrar() {

    override val supportsK2 = true

    /**
     * Registers extensions for the old (FE 1.0) and the new K2 compiler frontend (FIR).
     */
    override fun ExtensionStorage.registerExtensions(
        configuration: CompilerConfiguration
    ) {
        //registerOldFrontend()
        //registerK2Frontend()

        registerBackend()
    }
}