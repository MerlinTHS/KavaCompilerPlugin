package io.mths.kava.gradle.plugins

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

fun KotlinCompilation<*>.hasPlugin(
    plugin: Plugin
) = target.project.hasPlugin(plugin)

fun Project.hasPlugin(
    plugin: Plugin
) = pluginManager.hasPlugin(plugin.id)