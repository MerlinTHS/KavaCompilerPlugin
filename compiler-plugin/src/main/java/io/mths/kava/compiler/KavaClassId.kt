package io.mths.kava.compiler

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol
import org.jetbrains.kotlin.name.ClassId

object KavaClassId {
    val Exception = ClassId.fromString("java/lang/Exception")
    val KavaContext = ClassId.fromString("io/mths/kava/KavaContext")
    val KavaAnnotation = ClassId.fromString("io/mths/kava/Kava")

    val Optional = ClassId.fromString("java/util/Optional")
}

context (IrPluginContext)
fun irClassSymbolOf(id: ClassId): IrClassSymbol {
    return referenceClass(id)
        ?: throw IllegalState.noClassFor(id)
}