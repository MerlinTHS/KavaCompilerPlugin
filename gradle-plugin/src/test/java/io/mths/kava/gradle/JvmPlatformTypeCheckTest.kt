package io.mths.kava.gradle

import io.mths.kava.gradle.platform.isJvm
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse

class JvmPlatformTypeCheckTest {
    @Test
    fun jvm() {
        assert(KotlinPlatformType.jvm.isJvm())
    }

    @Test
    fun androidJvm() {
        assert(KotlinPlatformType.androidJvm.isJvm())
    }

    @Test
    fun `javascript is not jvm`() {
        assertFalse(KotlinPlatformType.js.isJvm())
    }
}