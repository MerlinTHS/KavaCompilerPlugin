package io.mths.kava.gradle.config

import io.mths.kava.gradle.config.multiplatform.configureMultiPlatform
import io.mths.kava.gradle.config.singleplatform.configureSinglePlatform
import io.mths.kava.gradle.platform.isMultiPlatform
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

internal fun KotlinCompilation<*>.configurePlatformSpecificSettings() {
    if (isMultiPlatform()) {
        configureMultiPlatform()
    } else {
        configureSinglePlatform()
    }
}