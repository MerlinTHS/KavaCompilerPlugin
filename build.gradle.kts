buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.24.0")

        classpath("io.github.merlinths:gradle-plugin:4.0.0")
    }
}
plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.8.20-Beta" apply false
    id("com.github.johnrengelman.shadow") version "8.1.0" apply false
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://maven.google.com")
        maven("https://plugins.gradle.org/m2/")
        google()
    }
}