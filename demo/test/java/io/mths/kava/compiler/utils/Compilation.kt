package io.mths.kava.compiler.utils

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import io.mths.kava.compiler.KavaPluginRegistrar
import org.intellij.lang.annotations.Language

fun compile(
    @Language("kotlin") code: String,
) = KotlinCompilation().apply {
    includeRuntime = true
    inheritClassPath = true
    messageOutputStream = System.out

    //configureCompiler()
    addPluginComponents()
    addSources(code)
}.compile()

private fun KotlinCompilation.addSources(
    @Language("kotlin") code: String
) {
    sources = listOf(
        SourceFile.kotlin("main.kt", code)
    )
}

private fun KotlinCompilation.addPluginComponents() {
    compilerPluginRegistrars = listOf(KavaPluginRegistrar())
    commandLineProcessors = listOf(MockedCommandLineProcessor())
}

private fun KotlinCompilation.configureCompiler() {
    languageVersion = "2.0"
    supportsK2 = true
    verbose = true
    useIR = true
}