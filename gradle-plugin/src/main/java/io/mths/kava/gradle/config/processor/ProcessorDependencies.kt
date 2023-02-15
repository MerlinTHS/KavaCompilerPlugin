package io.mths.kava.gradle.config.processor

import io.mths.kava.gradle.Dependency
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

internal fun KotlinCompilation<*>.addProcessorDependencies(
    kspConfigs: List<String>
) {
    target.project.dependencies {
        for (config in kspConfigs) {
            add(config, Dependency.processor)
        }
    }
}