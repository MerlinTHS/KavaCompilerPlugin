package io.mths.kava.gradle.plugins

import org.gradle.api.plugins.PluginManager

fun PluginManager.applyKspPlugin() {
    withPlugin(Plugin.Multiplatform.id) {
        apply(Plugin.Ksp.id)
    }

    withPlugin("application") {
        apply(Plugin.Ksp.id)
    }
}