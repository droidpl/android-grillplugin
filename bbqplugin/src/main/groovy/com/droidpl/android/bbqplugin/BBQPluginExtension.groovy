package com.droidpl.android.bbqplugin

import org.gradle.api.Project

/**
 * Plugin extension to allow configurations on the current plugin.
 */
public class BBQPluginExtension {

    /**
     * The project on which this extension is applied.
     */
    private Project project

    /**
     * Enables the debug mode to print some useful info
     */
    private boolean debugEnabled = false

    /**
     * Constructor that uses the project.
     * @param project The project with the plugin applied.
     */
    public BBQPluginExtension(Project project){
        this.project = project
        //Configure CI
        this.ext.CI = new CIHelper()
    }

    def debug(boolean debugEnabled){
        this.debugEnabled = debugEnabled
    }

    /**
     * Ensures that the extension is correctly set.
     */
    public void ensureValid(){
        //TODO add checks for the parameters needed
    }

    public void createTasks(){
        printDebugInfo()
        //TODO create documentation, testing and sonar tasks
    }

    private void printDebugInfo(){
        if(this.debugEnabled){
            println "------- BBQ BUILD INFO--------"
            println "Revision: ${CI.getCommitRevision()}"
            println "Branch: ${CI.getBranch()}"
            println "Tag: ${CI.getTag()}"
            println "-------------------------"
        }
    }
}
