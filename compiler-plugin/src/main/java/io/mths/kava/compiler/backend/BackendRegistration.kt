package io.mths.kava.compiler.backend

import io.mths.kava.compiler.backend.ir.KavaTransformer
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar

fun CompilerPluginRegistrar.ExtensionStorage.registerBackend() {
    IrGenerationExtension.registerExtension(
        KavaTransformer()
    )
}