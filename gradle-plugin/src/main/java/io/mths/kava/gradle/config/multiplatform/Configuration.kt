package io.mths.kava.gradle.config.multiplatform

import io.mths.kava.gradle.config.processor.configureAnnotationProcessor
import io.mths.kava.gradle.platform.isJvm
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

fun KotlinCompilation<*>.configureMultiPlatform() {
    configureAnnotationProcessor(MultiPlatformProcessorConfig)
}