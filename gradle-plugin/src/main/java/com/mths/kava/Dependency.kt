package com.mths.kava

import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact

internal object Dependency {
    val core = ("com.github.MerlinTHS:Kava:" + Version.core)

    val subPlugin = SubpluginArtifact(
        groupId = "com.mths.kava",
        artifactId = "compiler-plugin",
        version = Version.subPlugin
    )
}