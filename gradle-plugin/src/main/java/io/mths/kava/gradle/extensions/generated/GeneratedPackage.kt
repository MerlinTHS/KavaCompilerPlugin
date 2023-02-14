package io.mths.kava.gradle.extensions.generated

sealed interface GeneratedPackage {
    object Infer : GeneratedPackage

    data class Static(
        val name: String
    ) : GeneratedPackage
}