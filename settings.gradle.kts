include("gradle-plugin")
include("compiler-plugin")
include("intellij-plugin")
include("demo")

dependencyResolutionManagement {
    versionCatalogs {
        val kotlinVersion = "1.8.20-Beta"
        create("libs") {
            library("autoService", "com.google.auto.service", "auto-service")
                .version("1.0.1")
            library("embeddableCompiler", "org.jetbrains.kotlin", "kotlin-compiler-embeddable")
                .version(kotlinVersion)
            library("gradle-pluginApi", "org.jetbrains.kotlin", "kotlin-gradle-plugin-api")
                .version(kotlinVersion)
        }

        create("testLibs") {
            library("kotlinCompile", "dev.zacsweers.kctfork", "core")
                .version("0.2.1")
            library("internalCompile", "org.jetbrains.kotlin", "kotlin-compiler-internal-test-framework")
                .version(kotlinVersion)
        }
    }
}