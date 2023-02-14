package com.mths.kava.demo

import io.mths.kava.result.onFailure
import io.mths.kava.validator.*

fun main() {
    validate {
        val name by mayGetName()
        val age by mayGetAge()

        println("$name is $age years old!")
    } onFailure {
        println("Something went wrong!")
    }
}

fun mayGetName() =
    optional { "Peter" }

fun mayGetAge() =
    nullable { 26 }