package com.droidpl.android.bbqplugin.tasks

import org.gradle.api.Project
import org.gradle.testing.jacoco.tasks.JacocoReport

public class TestCoverageTask extends TaskConfigurer{




    @Override
    void checkPreconditions() {

    }

    @Override
    void configureOn(Project project) {

        Android.getVariants(project).all { variant ->
            project.tasks.create(name: "create${variant.getName().capitalize()}UnitTestCoverageReport", type: JacocoReport, dependsOn: "test${variant.getName().capitalize()}UnitTest") {
                group = "bbq"

                def baseLocation = "${project.getBuildDir()}/intermediates/classes"
                if(variant.getFlavorName() != null){
                    baseLocation += "/${variant.getFlavorName()}"
                }
                baseLocation += "/${variant.getBuildType().getName()}/"

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
        }
    }
}
