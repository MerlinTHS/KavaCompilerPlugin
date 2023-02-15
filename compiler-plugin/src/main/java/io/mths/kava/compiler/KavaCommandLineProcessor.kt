package io.mths.kava.compiler

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

val KEY_ENABLED = CompilerConfigurationKey<Boolean>("whether Kava is enabled")
@AutoService(CommandLineProcessor::class)
class KavaCommandLineProcessor : CommandLineProcessor {
    override val pluginId: String = "kavaPlugin"

    override val pluginOptions: Collection<CliOption> = listOf(
        CliOption(
            optionName = "enabled",
            valueDescription = "<true | false>",
            description = "whether to enable Kava or not",
            required = true
        )
    )

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration
    ) = when (option.optionName) {
        "enabled" -> configuration.put(KEY_ENABLED, value.toBoolean())
        else -> configuration.put(KEY_ENABLED, true)
    }
}