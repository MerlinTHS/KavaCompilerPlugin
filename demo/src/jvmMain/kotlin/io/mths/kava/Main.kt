package io.mths.kava
/*

annotation class KavaSynthetic

@KavaSynthetic
class Hello {
    fun foo() {

    }
}

fun getName(): String? = "Peter"

data class Age(val years: Int)

@Kava
fun getAge() = Age(3)

@Kava
fun useOptional(): String {
    if (8 > 4) {
        return ""
    } else if ("kkk" == "k") {
        return "Someone"
    }

    return getName()!!
}

@KavaBridge
@Suppress("TYPE_MISMATCH")
fun <Type> either(
    blockOne: @Kava () -> Type,
    blockTwo: @Kava () -> Type
): Optional<Type> {
    val resultOne: Optional<Type> = blockOne()
    val resultTwo: Optional<Type> = blockTwo()

    if (resultOne.isPresent)
        return resultOne
    else
        return resultTwo
}

fun caller(): Optional<String> {
    val name = useOptional()

    ensure (name.isNotBlank())
    println("The person is valid and its name is $name")
    return Optional.empty()
}

fun main() {
    val opt = optional {
        println("Hello")
    }
    println(opt)

    val second = either(
        blockOne = {
            "Hey"
        },
        blockTwo = {
            "Bye"
        }
    )
    println("Second is $second")

    val myKava = @Kava {
        "Hkkk"
    }

    val firstResult = optional {
        println("It is $it")
        "HHHH"
    }
    //val firstResult = optional(::doSomething)

    if (firstResult.isEmpty) {
        println("First result is empty!")
    } else {
        println("First result is not empty! (${firstResult.get()})")
    }
    val result = caller()

    if (result.isEmpty) {
        println("Something went wrong!")
    }
}









*/

import io.mths.kava.result.onFailure
import io.mths.kava.result.onSuccess
import io.mths.kava.validator.validate
import java.util.Optional


annotation class KavaBridge

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
annotation class Kava

@Kava
fun ensure(condition: Boolean) {
    if (!condition) {
        fail()
    }

    return println("Ensured!")
}

@Kava
fun fail(): Nothing =
    throw IllegalStateException("Kava should have replaced this function call with a local return!")

@KavaBridge
@Suppress("TYPE_MISMATCH")
fun <Type> optional(
    block: @Kava (String) -> Type
): Optional<Type> {
    return block("Default Name")
}



// -------------------------------------------------------------------------

data class User(
    val name: String,
    val isAdmin: Boolean,
    val iban: String,
)

fun main() {
    val user = User("Peter", isAdmin = false, iban = "invalid iban")

    validate {
        checkUser(user)
    } onSuccess {
        println("Everything fine")
    } onFailure {
        println("Bad world!")
    }
}

fun checkUser(user: User): Optional<String> {
    ensure(user.isAdmin)
    ensure(user.name.isNotBlank())

    validateIBAN(user.iban)
    return Optional.empty()
}

@Kava
fun validateIBAN(iban: String): Unit {
    ensure(iban.isNotBlank())
}















