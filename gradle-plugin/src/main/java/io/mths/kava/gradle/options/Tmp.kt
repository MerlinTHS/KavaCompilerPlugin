package io.mths.kava.gradle.options

/*
            add(SubpluginOption("mode", mode.name))

            when (val generatedFiles = processor.generatedFiles) {
                is GeneratedFiles.Separate -> {
                    add(SubpluginOption("processor.separateFiles", "true"))

                    when (val packageName = generatedFiles.packageName) {
                        is GeneratedPackage.Infer -> {
                            add(SubpluginOption("processor.separate.package-type", "infer"))
                        }
                        is GeneratedPackage.Static -> {
                            add(SubpluginOption("processor.separate.package-type", "static"))
                            add(SubpluginOption("processor.separate.package", packageName.name))
                        }
                    }

                    when (val fileExtension = generatedFiles.fileExtension) {
                        is GeneratedFileExtension.Static -> {
                            add(SubpluginOption("processor.separate.extension-type", "static"))
                            add(SubpluginOption("processor.separate.extension", fileExtension.extension))
                        }
                        is GeneratedFileExtension.Hashcode -> {
                            add(SubpluginOption("processor.separate.extension-type", "hashcode"))
                        }
                        is GeneratedFileExtension.None -> {}
                    }
                }
                is GeneratedFiles.Single -> {
                    add(SubpluginOption("processor.single.name", generatedFiles.name))
                    add(SubpluginOption("processor.single.packageName", generatedFiles.packageName))
                }
            }
 */