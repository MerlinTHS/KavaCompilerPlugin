package io.mths.kava.compiler.frontend.old

import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.descriptorUtil.builtIns
import org.jetbrains.kotlin.resolve.extensions.AnalysisHandlerExtension
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension

class KavaSyntheticsExtension : SyntheticResolveExtension {
    init {
        println("Initialized!")
    }

    override fun getSyntheticFunctionNames(
        thisDescriptor: ClassDescriptor
    ) = buildList<Name> {
        println("Getting names for ${thisDescriptor.name.asString()}")

        if (thisDescriptor.hasKavaAnnotation) {
            add(Name.identifier("kavaSynthetic"))
        }
    }

    private val ClassDescriptor.hasKavaAnnotation get() =
        annotations.hasAnnotation(FqName("io.mths.kava.KavaSynthetic"))

    override fun generateSyntheticMethods(
        thisDescriptor: ClassDescriptor,
        name: Name,
        bindingContext: BindingContext,
        fromSupertypes: List<SimpleFunctionDescriptor>,
        result: MutableCollection<SimpleFunctionDescriptor>
    ) {
        println("--| ${name.asString()} |---")

        if (name.asString() == "kavaSynthetic") {
            result.add(createKavaSyntheticGetter(thisDescriptor))
        }
        /*
        val slices = bindingContext.getSliceContents(BindingContext.FUNCTION)
            .onEach { (t, u) ->
                println("\t$t -> $u")
            }
        val first : SimpleFunctionDescriptor = slices.values.first()
        val second = slices.values.asList()[1]
        println("\tFirst is ${first.name.asString()}")
        println("\tSecond is ${second.name.asString()}")
        val newFirst = first.newCopyBuilder()
            .setReturnType(second.returnType!!)
            .build()!!
        bindingContext
        slices.put(slices.keys.first(), newFirst)*/
    }

    private fun createKavaSyntheticGetter(
        containingClass: ClassDescriptor
    ): SimpleFunctionDescriptor {
        val function = SimpleFunctionDescriptorImpl.create(
            containingClass,
            Annotations.EMPTY,
            Name.identifier("kavaSynthetic"),
            CallableMemberDescriptor.Kind.SYNTHESIZED,
            containingClass.source
        )

        val returnType = containingClass.builtIns.stringType

        function.initialize(
            null,
            containingClass.thisAsReceiverParameter,
            emptyList(),
            emptyList(),
            returnType,
            Modality.FINAL,
            DescriptorVisibilities.PUBLIC
        )

        return function
    }
}