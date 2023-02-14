package io.mths.kava.gradle.extensions.generated

sealed interface GeneratedFileExtension {
    object None : GeneratedFileExtension
    object Hashcode : GeneratedFileExtension

    data class Static(
        val extension: String
    ) : GeneratedFileExtension
}