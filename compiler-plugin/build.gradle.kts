import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("com.vanniktech.maven.publish")
    `maven-publish`
    signing

}

mavenPublishing {
    publishToMavenCentral()
}

group = "com.mths.kava"
version = "0.0.2"

dependencies {
    compileOnly(libs.autoService)
    kapt(libs.autoService)
    compileOnly(libs.embeddableCompiler)
}

tasks.register("sourcesJar", Jar::class) {
    group = "build"
    description = "Assembles Kotlin sources"

    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
    dependsOn(tasks.classes)
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
            artifact(tasks["sourcesJar"])

            pom {
                name.set("compiler-plugin")
                description.set("Kava Compiler Plugin")
                url.set("https://github.com/MerlinTHS/Kava")

                developers {
                    developer {
                        name.set("Merlin Eden")
                        url.set("https://github.com/MerlinTHS")
                    }
                }
            }
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
    compilerOptions.freeCompilerArgs.add("-opt-in=org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi")
}

