package io.mths.kava.gradle.config.processor

data class KavaProcessorConfig(
    val generatedDirectories: List<String>,
    val kspConfigs: List<String>
)