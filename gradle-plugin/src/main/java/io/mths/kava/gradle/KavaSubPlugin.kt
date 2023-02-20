package io.mths.kava.gradle

import io.mths.kava.gradle.config.configure
import io.mths.kava.gradle.extensions.createKavaExtension
import io.mths.kava.gradle.options.optionsProvider
import io.mths.kava.gradle.platform.isJvm
import io.mths.kava.gradle.plugins.applyKspPlugin
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class KavaSubPlugin : KotlinCompilerPluginSupportPlugin {
    override fun applyToCompilation(
        kotlinCompilation: KotlinCompilation<*>
    ): Provider<List<SubpluginOption>> {
        configure(kotlinCompilation)

        return kotlinCompilation.optionsProvider
    }

    override fun apply(target: Project) = with(target) {
        createKavaExtension()
        addRepositories()

        pluginManager.applyKspPlugin()
    }

    override fun getCompilerPluginId() =
        "kavaPlugin"

    /**
     * Ensures that every configured compilation is a JVM compilation,
     * so the configuration functions don't need any platform-type validation logic inside.
     */
    override fun isApplicable(
        kotlinCompilation: KotlinCompilation<*>
    ) = kotlinCompilation
        .platformType
        .isJvm()

    override fun getPluginArtifact(): SubpluginArtifact =
        Dependency.compilerPlugin
}

private fun Project.addRepositories() {
    repositories {
        mavenCentral()
    }
}