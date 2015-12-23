package com.github.droidpl.android.grillplugin.tasks

import com.android.build.gradle.api.BaseVariant
import com.github.droidpl.android.grillplugin.utils.Utils
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.tasks.JacocoReport

public class TestCoverageTask extends AbstractTaskConfigurer {

    //TODO enable merge DSL. Still not working properly
    private boolean merge

    @Override
    void checkPreconditions() {
        //No preconditions
    }

    @Override
    void configureOn(Project project) {
        project.plugins.apply(JacocoPlugin)
        project.tasks.withType(Test).whenTaskAdded {
            it.jacoco.append = false
            it.jacoco.classDumpFile = project.file("${project.buildDir}/jacoco/dump")
        }

        Utils.getVariants(project).all { variant ->
            Task task = project.tasks.create(name: getTaskName(variant), type: JacocoReport, dependsOn: getUnitTestTaskName(variant)) {
                group = "grill"
                def baseLocation = "${project.getBuildDir()}/intermediates/classes${Utils.buildVariantPath(variant)}"
                classDirectories = project.fileTree(
                        dir: "${baseLocation}",
                        excludes: ['**/R.class',
                                   '**/R$*.class',
                                   '**/*$ViewInjector*.*',
                                   '**/BuildConfig.*',
                                   '**/Manifest*.*',
                                   '**/*_*Factory.*']
                )
                sourceDirectories = project.files("src/main/java", "src/debug/java")
                executionData = project.files("${project.buildDir}/jacoco/test${variant.getName().capitalize()}UnitTest.exec")
                reports {
                    xml.enabled = false
                    html.enabled = true
                }
            }

            //Enable the report merging
            if(merge){
                def dest = "${project.buildDir}/outputs/code-coverage/connected/coverage.ec"
                Task unitTestTask = project.tasks.findByName(getUnitTestTaskName(variant))
                unitTestTask.jvmArgs "-javaagent:$project.buildDir/intermediates/jacoco/jacocoagent.jar=append=true,destfile=$dest"
                task.doFirst {
                    project.tasks.findByName("clean").execute()
                }
            }
        }
    }

    private String getTaskName(BaseVariant variant){
        String name = "create${variant.getName().capitalize()}UnitTestCoverageReport"
        if(merge){
            name += "Merged"
        }
        return name
    }

    private static String getUnitTestTaskName(BaseVariant variant){
        return "test${variant.getName().capitalize()}UnitTest"
    }
}
