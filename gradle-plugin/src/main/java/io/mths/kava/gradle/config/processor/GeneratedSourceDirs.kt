package io.mths.kava.gradle.config.processor

import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

internal fun KotlinCompilation<*>.addGeneratedSourceDirs(
    directories: List<String>
) {
    for (sourceSet in kotlinSourceSets) {
        directories.forEach(sourceSet.kotlin::srcDir)
    }
}