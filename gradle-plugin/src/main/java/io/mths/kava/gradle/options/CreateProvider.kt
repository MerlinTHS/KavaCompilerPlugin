package io.mths.kava.gradle.options

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

internal fun <Type> KotlinCompilation<*>.createProvider(
    supply: Project.() -> Type
) = with(target.project) {
    provider {
        supply()
    }
}