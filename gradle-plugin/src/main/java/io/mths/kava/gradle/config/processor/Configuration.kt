package io.mths.kava.gradle.config.processor

import io.mths.kava.gradle.plugins.Plugin
import io.mths.kava.gradle.plugins.hasPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

fun KotlinCompilation<*>.configureAnnotationProcessor(
    config: KavaProcessorConfig
) {
    if (hasPlugin(Plugin.Ksp)) {
        addProcessorDependencies(config.kspConfigs)
        addGeneratedSourceDirs(config.generatedDirectories)
    }
}