import io.mths.kava.gradle.extensions.generated.GeneratedFileExtension
import io.mths.kava.gradle.extensions.generated.GeneratedFiles
import io.mths.kava.gradle.extensions.generated.GeneratedPackage

plugins {
    kotlin("multiplatform")
    id("io.github.merlinths.kava")
}

repositories {
    mavenCentral()
}

kotlin {
    js()
    jvm()
}

kava {
    enabled = true

    processor {
        generatedFiles = GeneratedFiles.Separate(
            packageName = GeneratedPackage.Infer,
            fileExtension = GeneratedFileExtension.Static("Extensions")
        )
    }
}