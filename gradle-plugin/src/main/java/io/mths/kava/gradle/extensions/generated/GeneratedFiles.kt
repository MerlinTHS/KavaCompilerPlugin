package io.mths.kava.gradle.extensions.generated

sealed interface GeneratedFiles {
    data class Separate(
        val packageName: GeneratedPackage,
        val fileExtension: GeneratedFileExtension
    ) : GeneratedFiles

    data class Single(
        val name: String,
        val packageName: String
    ) : GeneratedFiles
}