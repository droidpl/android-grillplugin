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
     * @param properties The properties.
     */
    def codeQuality(Closure properties) {
        qualityTask = new SonarQualityTask()
        project.configure(qualityTask, properties)
    }

    /**
     * DSL Method.
     * Enables the documentation task.
     * @param properties Properties for the documentation task.
     */
    def documentation(Closure properties) {
        documentationTask = new DocumentationTask()
        project.configure(documentationTask, properties)
    }

    /**
     * DSL Method.
     * Enables the test coverage.
     * @param properties The properties to attach to the task.
     */
    def coverage(Closure properties) {
        testingTask = new TestCoverageTask()
        project.configure(testingTask, properties)
    }

    /**
     * DSL Method.
     * Enable the distribution properties.
     * @param properties The properties to attach to the task.
     */
    def distribute(Closure properties){
        distributionTask = new DistributionTask()
        project.configure(distributionTask, properties)
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
    }

    @Override
    void configureOn(Project project) {
        printDebugInfo()
        //Only create those tasks if this is an android project (library or app)
        if (Utils.isAndroidPlugin(project)) {
            if(qualityTask?.isEnabled()){
                qualityTask.configureOn(project)
            }
            if(documentationTask?.isEnabled()){
                documentationTask.configureOn(project)
            }
            if(testingTask?.isEnabled()){
                testingTask.configureOn(project)
            }
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
