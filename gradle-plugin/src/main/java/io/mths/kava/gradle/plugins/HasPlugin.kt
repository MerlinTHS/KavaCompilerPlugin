package io.mths.kava.gradle.plugins

import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

fun KotlinCompilation<*>.hasPlugin(plugin: Plugin) =
    target
        .project
        .pluginManager
        .hasPlugin(plugin.id)