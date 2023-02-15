package io.mths.kava.gradle.config.singleplatform

import io.mths.kava.gradle.config.processor.KavaProcessorConfig

internal val SinglePlatformProcessorConfig = KavaProcessorConfig(
    generatedDirectories = listOf(
        "build/generated/ksp/main/kotlin",
        "build/generated/ksp/test/kotlin"
    ),
    kspConfigs = listOf(
        "ksp",
        "kspTest"
    )
)