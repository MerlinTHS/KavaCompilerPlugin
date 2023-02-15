package io.mths.kava.gradle.config.multiplatform

import io.mths.kava.gradle.config.processor.KavaProcessorConfig

// TODO: Add android configurations
internal val MultiPlatformProcessorConfig = KavaProcessorConfig(
    generatedDirectories = listOf(
        "build/generated/ksp/jvm/jvmMain/kotlin",
        "build/generated/ksp/jvm/jvmTest/kotlin"
    ),
    kspConfigs = listOf(
        "kspJvm",
        "kspJvmTest"
    )
)