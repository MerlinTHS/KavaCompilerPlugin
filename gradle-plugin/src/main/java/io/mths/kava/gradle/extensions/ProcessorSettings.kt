package io.mths.kava.gradle.extensions

import io.mths.kava.gradle.extensions.generated.GeneratedFileExtension
import io.mths.kava.gradle.extensions.generated.GeneratedFiles
import io.mths.kava.gradle.extensions.generated.GeneratedPackage

open class ProcessorSettings {
    var generatedFiles: GeneratedFiles =
        GeneratedFiles.Separate(
            packageName = GeneratedPackage.Infer,
            fileExtension = GeneratedFileExtension.Static("Extensions")
        )

    operator fun invoke(
        config: ProcessorSettings.() -> Unit
    ) {
        config()
    }
}