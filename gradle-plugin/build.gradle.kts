plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "1.1.0"
}

group = "io.github.merlinths"
version = "1.0.1"

dependencies {
    implementation(libs.gradle.pluginApi)

    implementation(gradleKotlinDsl())
    testImplementation(kotlin("test"))
}

gradlePlugin {
    website.set("https://github.com/MerlinTHS/KavaCompilerPlugin")
    vcsUrl.set("https://github.com/MerlinTHS/KavaCompilerPlugin")

    plugins {
        create("kavaGradlePlugin") {
            displayName = "Kava Gradle Plugin"
            description = "Gradle Plugin to simplify kotlin development with Kava."
            id = "io.github.merlinths.kava"
            implementationClass = "io.mths.kava.gradle.KavaSubPlugin"

            tags.set(
                listOf("kotlin", "kava", "validation")
            )
        }
    }
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
                name.set("Kava Gradle Plugin")
                description.set("Gradle Plugin for Kava development.")
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

tasks.test {
    useJUnitPlatform()
}