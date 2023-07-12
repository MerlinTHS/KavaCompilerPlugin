package io.mths.kava.compiler.utils

import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor

internal class MockedCommandLineProcessor : CommandLineProcessor {
    override val pluginId = "MockedProcessor"
    override val pluginOptions = emptyList<CliOption>()
}