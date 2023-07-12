package io.mths.kava.compiler

import com.tschuchort.compiletesting.KotlinCompilation
import kotlin.test.Test
import kotlin.test.assertEquals
import io.mths.kava.compiler.utils.*

class KavaComponentRegistrarTest {
    @Test
    fun `Compiles successfully`() {
        val result = compile(
            """
            package io.mths.kava
            import java.lang.Exception
            import java.util.Optional

            // Required for test
            annotation class Kava
            annotation class KavaScope
            annotation class KavaSynthetic
            class KavaContext
            
            @KavaSynthetic
            class Hello {
            
            }

            fun getName() = "Peter"

            fun optional(
                block: () -> String
            ): Optional<String> {
                return Optional.of(block())
            }

            @Kava
            fun useOptional(example: String): String {
                return "Result"
            }

            @Kava
            fun safe() {
                val result = useOptional("Hello")
            }

            class Illegal

            fun main() {
                Illegal()
                useOptional("Bye")
                useOptional("Bye")
                useOptional("Bye")

                optional {
                    "Hello Kava!"
                }

                with("") {
                    useOptional("")
                }
            }
        """
        )

        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)
    }
}