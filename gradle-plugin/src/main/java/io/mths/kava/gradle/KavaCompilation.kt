package io.mths.kava.gradle

import org.gradle.kotlin.dsl.repositories
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

internal fun KotlinCompilation<*>.provideKavaOptions() =
    with(target.project) {
        provider(::kavaOptions)
    }

internal fun KotlinCompilation<*>.addMavenCentralRepository() {
    target.project.repositories {
        mavenCentral()
    }
}

internal fun KotlinCompilation<*>.addKavaDependency() {
    if (platformType.isJvm()) {
        kotlinSourceSets.forEach {
            it.dependencies {
                implementation(Dependency.core)
            }
        }
    }
}

internal fun KotlinCompilation<*>.addContextReceivers() {
    val contextArg = "-Xcontext-receivers"

    kotlinOptions {
        if (contextArg !in freeCompilerArgs) {
            freeCompilerArgs += contextArg
        }
    }
}