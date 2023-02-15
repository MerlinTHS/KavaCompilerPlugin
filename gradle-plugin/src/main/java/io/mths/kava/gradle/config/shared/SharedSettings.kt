package io.mths.kava.gradle.config.shared

import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

fun KotlinCompilation<*>.configureSharedSettings() {
    addContextReceivers()
    addKavaDependencies()
}