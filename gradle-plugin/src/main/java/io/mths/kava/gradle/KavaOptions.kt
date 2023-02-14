package io.mths.kava.gradle

import io.mths.kava.gradle.extensions.kavaExtension
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

internal val Project.kavaOptions: List<SubpluginOption>
    get() = with(kavaExtension) {
        listOf(
            SubpluginOption("enabled", enabled.toString())
        )
    }