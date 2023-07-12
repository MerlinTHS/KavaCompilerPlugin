plugins {
    id("java")
    kotlin("jvm")
    id("org.jetbrains.intellij") version "1.13.2"
}

repositories {
    mavenCentral()
    mavenLocal()
}

kotlin {
    jvmToolchain(11)
}

intellij {
    pluginName.set("Kava")
    version.set("2022.3.2")
    type.set("IC")

    plugins.set(
        listOf(
            "org.jetbrains.kotlin"
        )
    )
}

dependencies {
    implementation("io.github.merlinths:kava-compiler-plugin:2.0.2")
}