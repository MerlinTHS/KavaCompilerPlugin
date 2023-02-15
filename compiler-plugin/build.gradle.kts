import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("com.vanniktech.maven.publish")
    `maven-publish`
    signing

}

group = "io.github.merlinths"
version = "1.0.0"

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.Companion.S01, true)
    signAllPublications()

    coordinates(groupId = group.toString(), "kava-compiler-plugin", version.toString())
    pom {
        name.set("Kava Compiler Plugin")
        description.set("Kotlin Compiler Plugin to simplify development with kava.")

        inceptionYear.set("2023")
        url.set("https://github.com/merlinths/kava")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("merlinths")
                name.set("MerlinTHS")
                url.set("https://github.com/MerlinTHS/")
            }
        }

        scm {
            url.set("https://github.com/MerlinTHS/Kava/")
            connection.set("scm:git:git://github.com/merlinths/kava.git")
            developerConnection.set("scm:git:ssh://git@github.com/merlinths/kava.git")
        }
    }
}

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