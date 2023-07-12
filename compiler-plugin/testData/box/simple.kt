package io.mths.compiler.plugin.test

fun box(): String {
    val result = "Result"

    return if (result == "Result") {
        "OK"
    } else {
        "Fail: $result"
    }
}