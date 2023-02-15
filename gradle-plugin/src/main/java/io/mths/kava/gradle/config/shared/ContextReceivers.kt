package io.mths.kava.gradle.config.shared

import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

internal fun KotlinCompilation<*>.addContextReceivers() {
    val contextArg = "-Xcontext-receivers"

    kotlinOptions {
        if (contextArg !in freeCompilerArgs) {
            freeCompilerArgs += contextArg
        }
    }
}