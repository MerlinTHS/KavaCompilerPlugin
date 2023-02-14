package io.mths.kava.gradle

import io.mths.kava.gradle.extensions.createKavaExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class KavaSubPlugin : KotlinCompilerPluginSupportPlugin {
    override fun applyToCompilation(
        kotlinCompilation: KotlinCompilation<*>
    ): Provider<List<SubpluginOption>> = kotlinCompilation.run {
        addMavenCentralRepository()
        addKavaDependency()
        addContextReceivers()

        provideKavaOptions()
    }

    override fun apply(target: Project) {
        target.createKavaExtension()

        super.apply(target)
    }

    override fun getCompilerPluginId() = "kavaPlugin"

    override fun isApplicable(
        kotlinCompilation: KotlinCompilation<*>
    ): Boolean =
        kotlinCompilation
        .platformType
        .isJvm()

    override fun getPluginArtifact() =
        Dependency.compilerPlugin
}