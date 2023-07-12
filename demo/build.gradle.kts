import io.mths.kava.gradle.extensions.generated.GeneratedFileExtension
import io.mths.kava.gradle.extensions.generated.GeneratedFiles
import io.mths.kava.gradle.extensions.generated.GeneratedPackage

plugins {
    id("io.github.merlinths.kava")
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                //languageVersion = "2.0"
            }
        }
    }
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