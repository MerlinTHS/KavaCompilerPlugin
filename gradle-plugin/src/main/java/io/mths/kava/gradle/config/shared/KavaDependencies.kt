package io.mths.kava.gradle.config.shared

import io.mths.kava.gradle.Dependency
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal fun KotlinCompilation<*>.addKavaDependencies() {
    kotlinSourceSets.forEach { sourceSet ->
        sourceSet.addDependencies()
    }
}

private fun KotlinSourceSet.addDependencies() {
    dependencies {
        implementation(Dependency.core)
        implementation(Dependency.annotations)
    }
}