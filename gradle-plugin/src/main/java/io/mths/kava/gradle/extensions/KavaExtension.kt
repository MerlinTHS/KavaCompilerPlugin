package io.mths.kava.gradle.extensions

import org.gradle.api.Project

open class KavaExtension {
    var enabled = true
    var mode = KavaMode.Implicit

    var processor = ProcessorSettings()
}

internal fun Project.createKavaExtension() {
    extensions.create("kava", KavaExtension::class.java)
}

internal val Project.kavaExtension: KavaExtension
    get() = extensions.findByType(KavaExtension::class.java)
        ?: KavaExtension()