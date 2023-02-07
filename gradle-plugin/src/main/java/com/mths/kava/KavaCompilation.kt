package com.mths.kava

import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

internal fun KotlinCompilation<*>.provideKavaOptions() =
    with(target.project) {
        provider(::kavaOptions)
    }

internal fun KotlinCompilation<*>.addKavaRepository() {
    target.project.repositories {
        maven(url = "https://jitpack.io")
    }
}

internal fun KotlinCompilation<*>.addKavaDependency() {
    allKotlinSourceSets
        .find { "jvm" in it.name } // TODO: Remove when Kava is multiplatform
        ?.dependencies {
            implementation(Dependency.core)
        }
}

internal fun KotlinCompilation<*>.addContextReceivers() {
    val contextArg = "-Xcontext-receivers"

    kotlinOptions {
        if (contextArg !in freeCompilerArgs) {
            println("Adding context receivers to compiler arguments!")
            freeCompilerArgs += contextArg
        }
    }
}