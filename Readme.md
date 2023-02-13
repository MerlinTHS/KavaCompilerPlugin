<h1 align="center">Kava Compiler Plugin</h1>

A Kotlin compiler plugin to simplify development with [Kava](https://github.com/MerlinTHS/Kava),
including [gradle](/gradle-plugin)- and [intellij](/intellij-plugin) - plugin.

The plugin does currently only a few things:
- It adds dependencies to your JVM - compilations ( only multiplatform projects supported yet )
- It configures the annotation processor as well as the generated source directories of it
- It wraps the KSP options into a typesafe gradle extension

It also enables support for context receivers, which is required for using Kava.

See [Demo](/demo).