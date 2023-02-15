package io.mths.kava.gradle.options

import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

val KotlinCompilation<*>.optionsProvider: Provider<List<SubpluginOption>>
    get() = createProvider {
        compilerPluginOptions
    }