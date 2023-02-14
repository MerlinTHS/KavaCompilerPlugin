buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.24.0")

        classpath("io.github.merlinths:gradle-plugin:1.0.1")
    }
}
plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.8.0" apply false
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