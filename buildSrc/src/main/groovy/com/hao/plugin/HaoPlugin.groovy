package com.hao.plugin

import com.hao.plugin.extension.TinkerPatchExtension
import com.hao.plugin.task.TinkerManifestTask
import com.hao.plugin.task.TinkerPatchSchemaTask
import org.gradle.api.Plugin
import org.gradle.api.Project

public class HaoPlugin implements Plugin<Project> {

    private Project mProject

    @Override
    void apply(Project project) {
        println("=======================My Plugin Start==========================")
        mProject = project
        mProject.extensions.create("tinkerPatch", TinkerPatchExtension)

        def android = mProject.extensions.android

        mProject.afterEvaluate {
            def configuration = mProject.tinkerPatch
            println(configuration.toString())
            android.applicationVariants.all { variant ->
                def variantName = variant.name.capitalize()
                TinkerPatchSchemaTask tinkerPatchSchemaTask = mProject.tasks.create("tinkerPatch${variantName}", TinkerPatchSchemaTask)
                TinkerManifestTask tinkerManifestTask = mProject.tasks.create("tinkerProcess${variantName}Manifest", TinkerManifestTask)
            }
        }

        println("=======================My Plugin End============================")
    }
}