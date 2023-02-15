package io.mths.kava.gradle.platform

import io.mths.kava.gradle.util.isOneOf
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

internal fun KotlinPlatformType.isJvm(): Boolean =
    isOneOf(
        KotlinPlatformType.jvm,
        KotlinPlatformType.androidJvm
    )