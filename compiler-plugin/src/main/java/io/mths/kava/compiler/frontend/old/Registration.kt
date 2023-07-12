package io.mths.kava.compiler.frontend.old

import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.resolve.extensions.AnalysisHandlerExtension
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension

fun CompilerPluginRegistrar.ExtensionStorage.registerOldFrontend() {
    AnalysisHandlerExtension
        .registerExtension(PlatformTypeChecker())

    SyntheticResolveExtension
        .registerExtension(KavaSyntheticsExtension())
}