package io.mths.kava.gradle.config

import io.mths.kava.gradle.config.shared.configureSharedSettings
import io.mths.kava.gradle.platform.isJvm
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

fun configure(
    compilation: KotlinCompilation<*>
) = with(compilation) {
    if (platformType.isJvm()) {
        configureSharedSettings()
        configurePlatformSpecificSettings()
    }
}