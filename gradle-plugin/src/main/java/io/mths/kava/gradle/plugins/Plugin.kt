package io.mths.kava.gradle.plugins

enum class Plugin(
    val id: String
) {
    Multiplatform("org.jetbrains.kotlin.multiplatform"),
    Ksp("com.google.devtools.ksp"),
    Jvm("org.jetbrains.kotlin.jvm")
}