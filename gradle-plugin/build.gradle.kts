plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("java-gradle-plugin")
    `maven-publish`
}

group = "com.mths.kava"
version = "1.0.4"

dependencies {
    implementation(libs.gradle.pluginApi)

    implementation(gradleKotlinDsl())
}

gradlePlugin {
    plugins {
        create("kavaGradlePlugin") {
            id = "com.mths.kava"
            implementationClass = "com.mths.kava.KavaSubPlugin"
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
                name.set("com.mths.kava")
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