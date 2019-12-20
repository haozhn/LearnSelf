package com.hao.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class TinkerManifestTask extends DefaultTask {

    TinkerManifestTask() {
        group = 'tinker'
    }

    @TaskAction
    def updateManifest() {

    }
}