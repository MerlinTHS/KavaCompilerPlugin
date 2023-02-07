package com.mths.kava.demo

import com.github.merlinths.kava.validator.*

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