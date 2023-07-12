package io.mths.kava.compiler.backend.ir.transform

import org.jetbrains.kotlin.GeneratedDeclarationKey
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.expressions.IrBody
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl
import org.jetbrains.kotlin.ir.types.IrSimpleType
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.util.primaryConstructor
import org.jetbrains.kotlin.ir.util.render
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.ir.visitors.acceptChildrenVoid
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.renderer.render

abstract class AbstractTransformerForGenerator(
    protected val context: IrPluginContext,
    private val visitBodies: Boolean
) : IrElementVisitorVoid {
    protected val irFactory = context.irFactory
    protected val irBuiltIns = context.irBuiltIns

    abstract fun interestedIn(key: GeneratedDeclarationKey): Boolean
    abstract fun generateBodyForFunction(function: IrSimpleFunction, key: GeneratedDeclarationKey): IrBody?
    abstract fun generateBodyForConstructor(constructor: IrConstructor, key: GeneratedDeclarationKey): IrBody?

    final override fun visitElement(element: IrElement) {
        if (visitBodies) {
            element.acceptChildrenVoid(this)
        } else {
            when (element) {
                is IrDeclaration,
                is IrFile,
                is IrModuleFragment -> element.acceptChildrenVoid(this)
                else -> {}
            }
        }
    }

    final override fun visitSimpleFunction(declaration: IrSimpleFunction) {
        val origin = declaration.origin
        if (origin !is IrDeclarationOrigin.GeneratedByPlugin || !interestedIn(origin.pluginKey)) {
            if (visitBodies) {
                visitElement(declaration)
            }
            return
        }
        require(declaration.body == null)
        declaration.body = generateBodyForFunction(declaration, origin.pluginKey)
    }

    final override fun visitConstructor(declaration: IrConstructor) {
        val origin = declaration.origin
        if (origin !is IrDeclarationOrigin.GeneratedByPlugin || !interestedIn(origin.pluginKey) || declaration.body != null) {
            if (visitBodies) {
                visitElement(declaration)
            }
            return
        }
        declaration.body = generateBodyForConstructor(declaration, origin.pluginKey)
    }

    // ------------------------ utilities ------------------------

    protected fun generateDefaultBodyForMaterializeFunction(function: IrSimpleFunction): IrBody? {
        val constructedType = function.returnType as? IrSimpleType ?: return null
        val constructedClassSymbol = constructedType.classifier
        val constructedClass = constructedClassSymbol.owner as? IrClass ?: return null
        val constructor = constructedClass.primaryConstructor ?: return null
        val constructorCall = IrConstructorCallImpl(
            -1,
            -1,
            constructedType,
            constructor.symbol,
            typeArgumentsCount = 0,
            constructorTypeArgumentsCount = 0,
            valueArgumentsCount = 0
        )
        val returnStatement = IrReturnImpl(-1, -1, irBuiltIns.nothingType, function.symbol, constructorCall)
        return irFactory.createBlockBody(-1, -1, listOf(returnStatement))
    }

    protected fun generateBodyForDefaultConstructor(declaration: IrConstructor): IrBody? {
        val type = declaration.returnType as? IrSimpleType ?: return null

        val delegatingAnyCall = IrDelegatingConstructorCallImpl(
            -1,
            -1,
            irBuiltIns.anyType,
            irBuiltIns.anyClass.owner.primaryConstructor?.symbol ?: return null,
            typeArgumentsCount = 0,
            valueArgumentsCount = 0
        )

        val initializerCall = IrInstanceInitializerCallImpl(
            -1,
            -1,
            (declaration.parent as? IrClass)?.symbol ?: return null,
            type
        )

        return irFactory.createBlockBody(-1, -1, listOf(delegatingAnyCall, initializerCall))
    }
}