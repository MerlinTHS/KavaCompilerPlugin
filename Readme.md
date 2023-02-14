<h1 align="center">Kava Compiler Plugin</h1>

A Kotlin compiler plugin to simplify development with [Kava](https://github.com/MerlinTHS/Kava).
It ships with a [Gradle-Plugin](/gradle-plugin) to integrate it into your project
and an [IntelliJ-Plugin](/intellij-plugin) to gain better IDE - assistance.

The Gradle-Plugin does currently only a few things:
- Adding dependencies to your JVM - compilations
- Configuring the annotation processor as well as the generated source directories of it
- Wrapping the KSP options into a typesafe gradle extension
- Enabling support for [Context Receivers](https://github.com/Kotlin/KEEP/blob/master/proposals/context-receivers.md), which is required for using Kava.

See [Demo](/demo).

## Getting Started

The plugin is hosted on the [Gradle Plugin Portal](https://plugins.gradle.org/plugin/io.github.merlinths.kava).
Simply apply it in your _build.gradle.kts_.

```kotlin
plugins {
    id("io.github.merlinths.kava") version "1.0.0"
}
``` 

Now you can configure [Kavas Annotation Processor](https://github.com/MerlinTHS/Kava#custom-types) options in a typesafe manner. 

```kotlin
kava {
    processor {
        generatedFiles = GeneratedFiles.Separate(
            packageName = GeneratedPackage.Infer,
            fileExtension = GeneratedFileExtension.Static("Extensions")
        )
    }
}
```

## Supported Platforms

Kavas gradle-plugin can be added to any kotlin project, but will only be applied on
- JVM compilations
- Android (JVM) compilations

## Versioning

Since it's a compiler plugin, its implementation is tightly coupled to a specific compiler version.

| Kotlin Compiler |  Kava Plugin  |
|:---------------:|:-------------:|
|     `1.8.0`     |    `1.0.0`    |
