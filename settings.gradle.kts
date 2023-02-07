include("gradle-plugin")
include("compiler-plugin")
include("intellij-plugin")
include("demo")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.8.0")

            library("autoService", "com.google.auto.service", "auto-service")
                .version("1.0.1")
            library("embeddableCompiler", "org.jetbrains.kotlin", "kotlin-compiler-embeddable")
                .versionRef("kotlin")
            library("gradle-pluginApi", "org.jetbrains.kotlin", "kotlin-gradle-plugin-api")
                .versionRef("kotlin")
        }
    }
}