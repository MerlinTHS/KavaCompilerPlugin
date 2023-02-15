package io.mths.kava.gradle.options

import io.mths.kava.gradle.extensions.kavaExtension
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

val Project.compilerPluginOptions: List<SubpluginOption>
    get() = with(kavaExtension) {
        buildList {
            add(SubpluginOption("enabled", enabled.toString()))
        }
    }