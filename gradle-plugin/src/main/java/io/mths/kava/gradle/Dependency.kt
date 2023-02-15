package io.mths.kava.gradle

import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact

internal object Dependency {
    val core =
        dependency("kava-core", Version.core)
    val annotations =
        dependency("kava-annotations", Version.annotations)
    val processor =
        dependency("kava-processor", Version.processor)

    val compilerPlugin =
        SubpluginArtifact(groupId, "kava-compiler-plugin", Version.compilerPlugin)
}

private const val groupId =
    "io.github.merlinths"

private fun dependency(
    artifactId: String,
    version: String
) = "$groupId:$artifactId:$version"