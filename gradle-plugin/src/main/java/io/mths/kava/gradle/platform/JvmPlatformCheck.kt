package io.mths.kava.gradle.platform

import io.mths.kava.gradle.plugins.Plugin
import io.mths.kava.gradle.plugins.hasPlugin
import io.mths.kava.gradle.util.isOneOf
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

fun KotlinPlatformType.isJvm(): Boolean =
    isOneOf(
        KotlinPlatformType.jvm,
        KotlinPlatformType.androidJvm
    )

fun KotlinCompilation<*>.isJvmSinglePlatform(): Boolean =
    hasPlugin(Plugin.Jvm)