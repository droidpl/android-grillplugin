package com.droidpl.android.grillplugin

import com.droidpl.android.grillplugin.tasks.GrillPluginExtension
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
public class GrillPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.extensions.create("grill", GrillPluginExtension, project)
        project.afterEvaluate {
            GrillPluginExtension extension = project.grill as GrillPluginExtension
            extension.checkPreconditions()
            extension.configureOn(project)
        }
    }
}
