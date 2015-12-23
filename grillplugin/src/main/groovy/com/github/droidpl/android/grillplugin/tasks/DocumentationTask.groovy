package com.github.droidpl.android.grillplugin.tasks

import com.github.droidpl.android.grillplugin.utils.Utils
import org.gradle.api.Project
import org.gradle.api.tasks.javadoc.Javadoc

public class DocumentationTask extends AbstractTaskConfigurer {

    private String doclavaVersion = "1.0.6"

    private String templateDir = "/docs/template"

    def doclavaVersion(String version) {
        doclavaVersion = version
    }

    def templateDir(String dirName) {
        templateDir = dirName
    }

    @Override
    void checkPreconditions() {
        //No preconditions for the documentation plugin
    }

    @Override
    void configureOn(Project project) {
        project.configurations {
            docLava
        }
        project.dependencies {
            docLava "com.google.doclava:doclava:${doclavaVersion}"
        }
        Utils.getVariants(project).all { variant ->
            project.tasks.create(name: "documentation${variant.name.capitalize()}", type: Javadoc) {
                title = null
                description = "Generates doclava documentation for the build variant $variant.name."
                group = "grill"
                source = variant.javaCompile.source
                exclude '**/BuildConfig.java'
                exclude '**/R.java'
                options {
                    addStringOption "templatedir", "${project.projectDir}/..${templateDir}"
                    doclet "com.google.doclava.Doclava"
                    docletpath = project.configurations.docLava.files.asType(List)
                    bootClasspath new File(System.getenv('JAVA_HOME') + "/jre/lib/rt.jar")
                }
                //Ensure the classpath is prepared before running the build
                doFirst {
                    project.tasks.getByName("prepare${variant.name.capitalize()}Dependencies").execute()
                    def androidJar = "${project.android.sdkDirectory}/platforms/${project.android.compileSdkVersion}/android.jar"
                    classpath = project.files(variant.javaCompile.classpath.files.asType(List)) + project.files(androidJar)
                }
            }
        }
    }
}
