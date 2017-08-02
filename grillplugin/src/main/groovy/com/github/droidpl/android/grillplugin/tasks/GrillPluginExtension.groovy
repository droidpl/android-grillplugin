package com.github.droidpl.android.grillplugin.tasks

import com.github.droidpl.android.grillplugin.utils.CIHelper
import com.github.droidpl.android.grillplugin.utils.Utils
import org.gradle.api.Project

/**
 * Plugin extension to allow configurations on the current plugin.
 */
public class GrillPluginExtension extends AbstractTaskConfigurer {

    /**
     * The project on which this plugin is applied.
     */
    private Project project

    /**
     * Enables the debug mode to print some useful info
     */
    private boolean debugEnabled = false

    /**
     * Quality task.
     */
    private SonarQualityTask qualityTask

    /**
     * Testing task.
     */
    private TestCoverageTask testingTask

    /**
     * Documentation task.
     */
    private DocumentationTask documentationTask

    /**
     * The distribution task.
     */
    private DistributionTask distributionTask

    /**
     * The google play distribution task.
     */
    private GooglePlayPublishTask googlePlayPublishTask

    /**
     * Constructor that uses the project.
     * @param project The project with the plugin applied.
     */
    public GrillPluginExtension(Project project) {
        //Configure CI
        this.ext.CI = new CIHelper()
        this.project = project
    }

    /**
     * DSL Method.
     * Enables the debug mode.
     * @param debugEnabled True if it should be enabled. False otherwise.
     */
    def debug(boolean debugEnabled) {
        this.debugEnabled = debugEnabled
    }

    /**
     * DSL Method
     * Enables the code quality feature and tasks.
     * @param closure The properties.
     */
    def codeQuality(Closure closure) {
        qualityTask = new SonarQualityTask()
        project.configure(qualityTask, closure)
    }

    /**
     * DSL Method.
     * Enables the documentation task.
     * @param closure Properties for the documentation task.
     */
    def documentation(Closure closure) {
        documentationTask = new DocumentationTask()
        project.configure(documentationTask, closure)
    }

    /**
     * DSL Method.
     * Enables the test coverage.
     * @param closure The properties to attach to the task.
     */
    def coverage(Closure closure) {
        testingTask = new TestCoverageTask()
        project.configure(testingTask, closure)
    }

    /**
     * DSL Method.
     * Enable the distribution properties.
     * @param closure The properties to attach to the task.
     */
    def distribute(Closure closure){
        distributionTask = new DistributionTask()
        project.configure(distributionTask, closure)
    }

    /**
     * DSL Method
     * Adds the google play configuration to auto upload an apk
     * @param closure The closure to upload the apk
     */
    def googlePlay(Closure closure){
        googlePlayPublishTask = new GooglePlayPublishTask()
        project.configure(googlePlayPublishTask, closure)
    }

    @Override
    void checkPreconditions() {
        if(qualityTask?.isEnabled()){
            qualityTask.checkPreconditions()
        }
        if(testingTask?.isEnabled()){
            testingTask.checkPreconditions()
        }
        if(documentationTask?.isEnabled()){
            documentationTask.checkPreconditions()
        }
        if(distributionTask?.isEnabled()){
            distributionTask.checkPreconditions()
        }
        if(googlePlayPublishTask?.isEnabled()){
            googlePlayPublishTask.checkPreconditions()
        }
    }

    @Override
    void configureOn(Project project) {
        printDebugInfo()
        //Only create those tasks if this is an android project (library or app)
        if (Utils.isAndroidPlugin(project)) {
            if(googlePlayPublishTask?.isEnabled()){
                googlePlayPublishTask.configureOn(project)
            }
        }
        if(qualityTask?.isEnabled()){
            qualityTask.configureOn(project)
        }
        if(documentationTask?.isEnabled()){
            documentationTask.configureOn(project)
        }
        if(testingTask?.isEnabled()){
            testingTask.configureOn(project)
        }
        if(distributionTask?.isEnabled()){
            distributionTask.configureOn(project)
        }
    }

    /**
     * Prints some debug information.
     */
    private void printDebugInfo() {
        if (this.debugEnabled) {
            println "----- GRILL BUILD INFO ------"
            println "Revision: ${CI.getCommitRevision()}"
            println "Branch: ${CI.getBranch()}"
            println "Tag: ${CI.getTag()}"
            println "-------------------------"
        }
    }
}
