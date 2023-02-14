package io.mths.kava.gradle

import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact

internal object Dependency {
    val core = ("io.github.merlinths:kava-core:" + Version.core)

    val compilerPlugin = SubpluginArtifact(
        groupId = "io.github.merlinths",
        artifactId = "kava-compiler-plugin",
        version = Version.compilerPlugin
    )
}