import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("com.vanniktech.maven.publish")
    `maven-publish`
    signing

}

group = "io.github.merlinths"
version = "2.0.2"

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.Companion.S01, true)
    signAllPublications()

    coordinates(groupId = group.toString(), "kava-compiler-plugin", version.toString())
    pom {
        name.set("Kava Compiler Plugin")
        description.set("Kotlin Compiler Plugin to simplify development with kava.")

        inceptionYear.set("2023")
        url.set("https://github.com/MerlinTHS/KavaCompilerPlugin")

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
            url.set("https://github.com/MerlinTHS/KavaCompilerPlugin/")
            connection.set("scm:git:git://github.com/merlinths/kavacompilerplugin.git")
            developerConnection.set("scm:git:ssh://git@github.com/merlinths/kavacompilerplugin.git")
        }
    }
}

dependencies {
    compileOnly(libs.autoService)
    kapt(libs.autoService)
    compileOnly(libs.embeddableCompiler)

    implementation("io.github.merlinths:kava-annotations:1.0.4")

    testImplementation(testLibs.kotlinCompile)
    testImplementation(kotlin("test"))

    testImplementation("io.mockk:mockk:1.13.4")
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
            //artifact(tasks["sourcesJar"])

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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf("-Xcontext-receivers")
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}