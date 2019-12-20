package com.hao.plugin.task

import com.hao.plugin.extension.TinkerPatchExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class TinkerPatchSchemaTask extends DefaultTask {

    TinkerPatchExtension configuration
    def android

    TinkerPatchSchemaTask() {
        description = 'Assemble Tinker Patch'
        group = 'tinker'
        outputs.upToDateWhen { false }
        configuration = project.tinkerPatch

        android = project.extensions.android
    }

    @TaskAction
    def tinkerPatch() {

    }
}