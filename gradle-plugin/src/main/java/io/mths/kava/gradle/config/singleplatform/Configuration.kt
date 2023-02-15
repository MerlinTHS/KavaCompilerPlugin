package io.mths.kava.gradle.config.singleplatform

import io.mths.kava.gradle.config.processor.configureAnnotationProcessor
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

fun KotlinCompilation<*>.configureSinglePlatform() {
    configureAnnotationProcessor(SinglePlatformProcessorConfig)
}