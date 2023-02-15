package io.mths.kava.gradle.platform

import io.mths.kava.gradle.plugins.Plugin
import io.mths.kava.gradle.plugins.hasPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

fun KotlinCompilation<*>.isJvmSinglePlatform(): Boolean =
    hasPlugin(Plugin.Jvm)