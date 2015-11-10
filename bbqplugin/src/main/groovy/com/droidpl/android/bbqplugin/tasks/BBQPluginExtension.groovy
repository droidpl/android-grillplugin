package com.droidpl.android.bbqplugin.tasks

import com.droidpl.android.bbqplugin.utils.CIHelper
import com.droidpl.android.bbqplugin.utils.Utils
import org.gradle.api.Project

/**
 * Plugin extension to allow configurations on the current plugin.
 */
public class BBQPluginExtension extends TaskConfigurer {

    /**
     * The project on which this plugin is applied.
     */
    private Project project

    /**
     * Enables the debug mode to print some useful info
     */
    private boolean debugEnabled = false

    private SonarQualityTask qualityTask
    private TestCoverageTask testingTask
    private DocumentationTask documentationTask

    /**
     * Constructor that uses the project.
     * @param project The project with the plugin applied.
     */
    public BBQPluginExtension(Project project){
        //Configure CI
        this.ext.CI = new CIHelper()
        this.project = project
    }

    /**
     * DSL Method.
     * Enables the debug mode.
     * @param debugEnabled True if it should be enabled. False otherwise.
     */
    def debug(boolean debugEnabled){
        this.debugEnabled = debugEnabled
    }

    /**
     * DSL Method
     * Enables the code quality feature and tasks.
     * @param properties The properties.
     */
    def codeQuality(Closure properties){
        qualityTask = new SonarQualityTask(project)
        project.configure(qualityTask, properties)
    }

    @Override
    void checkPreconditions() {
        qualityTask?.checkPreconditions()
        testingTask?.checkPreconditions()
        documentationTask?.checkPreconditions()
    }

    @Override
    void configureOn(Project project) {
        printDebugInfo()
        qualityTask?.configureOn(project)
        //Only create those tasks if this is an android project (library or app)
        if(Utils.isAndroidPlugin(project)){
            testingTask?.configureOn(project)
            documentationTask?.configureOn(project)
        }
    }

    /**
     * Prints some debug information.
     */
    private void printDebugInfo(){
        if(this.debugEnabled){
            println "------- BBQ BUILD INFO --------"
            println "Revision: ${CI.getCommitRevision()}"
            println "Branch: ${CI.getBranch()}"
            println "Tag: ${CI.getTag()}"
            println "-------------------------"
        }
    }
}
