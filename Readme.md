<h1 align="center">Kava Compiler Plugin</h1>

A Kotlin compiler plugin to simplify development with [Kava](https://github.com/MerlinTHS/Kava),
including [gradle](/gradle-plugin)- and [intellij](/intellij-plugin) - plugin.

The plugin does currently only a few things:
- It adds dependencies to your JVM - compilations ( only multiplatform projects supported yet )
- It configures the annotation processor as well as the generated source directories of it
- It wraps the KSP options into a typesafe gradle extension

It also enables support for context receivers, which is required for using Kava.

See [Demo](/demo).

# Installation

The plugin is hosted on the [Gradle Plugin Portal](https://plugins.gradle.org/plugin/io.github.merlinths.kava).
Simply include the id with its [latest version](#versioning).

```kotlin
plugins {
    id("io.github.merlinths.kava") version "1.0.0"
}
``` 

Now you can configure kavas annotation processor options in a typesafe manner. 

```kotlin
kava {
    seperateFiles = true
}
```

# Supported Platforms

Kavas gradle-plugin can be added to any kotlin project, but will only be applied on
- JVM compilations
- Android (JVM) compilations

# Versioning

Since it's a compiler plugin, its implementation is tightly coupled to a specific compiler version.

| Kotlin Compiler | *Kava Plugin* |
|:---------------:|:-------------:|
|     `1.8.0`     |    `1.0.0`    |
