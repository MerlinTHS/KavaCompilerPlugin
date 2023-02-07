package com.mths.kava

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

internal val Project.kavaOptions: List<SubpluginOption>
    get() = with(kavaExtension) {
        println("Kava is running in mode [${mode.name}]")
        listOf(
            SubpluginOption("enabled", enabled.toString())
        )
    }