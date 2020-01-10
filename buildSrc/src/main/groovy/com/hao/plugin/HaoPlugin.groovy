package com.hao.plugin

import com.android.build.gradle.AppExtension
import com.hao.plugin.transform.CostTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

public class HaoPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {
        println("=======================My Plugin Start==========================")
        def android = project.extensions.findByType(AppExtension.class)
        android.registerTransform(new CostTransform(project))
        println("=======================My Plugin End============================")
    }
}