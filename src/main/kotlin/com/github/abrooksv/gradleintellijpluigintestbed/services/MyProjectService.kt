package com.github.abrooksv.gradleintellijpluigintestbed.services

import com.intellij.openapi.project.Project
import com.github.abrooksv.gradleintellijpluigintestbed.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
