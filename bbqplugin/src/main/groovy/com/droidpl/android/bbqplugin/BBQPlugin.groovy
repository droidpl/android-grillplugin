package com.droidpl.android.bbqplugin

import com.droidpl.android.bbqplugin.tasks.BBQPluginExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * The plugin that enhances your android project. This plugin allows to add:
 * <ol>
 * <li>Continuous integration tips</li>
 * <li>Unit testing</li>
 * <li>Unit test code coverage: Jacoco</li>
 * <li>Code quality: Sonar</li>
 * <li>Documentation: Doclava</li>
 * </ol>
 */
public class BBQPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.extensions.create("BBQ", BBQPluginExtension, project)
        project.afterEvaluate {
            BBQPluginExtension extension = project.BBQ as BBQPluginExtension
            extension.checkPreconditions()
            extension.configureOn(project)
        }
    }
}
