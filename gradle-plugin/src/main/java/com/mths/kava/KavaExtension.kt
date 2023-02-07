package com.mths.kava

import org.gradle.api.Project

open class KavaExtension {
    var enabled: Boolean = true
    var mode: KavaMode = KavaMode.Implicit
}

internal fun Project.createKavaExtension() {
    extensions.create("kava", KavaExtension::class.java)
}

internal val Project.kavaExtension: KavaExtension
    get() = extensions.findByType(KavaExtension::class.java)
        ?: KavaExtension()