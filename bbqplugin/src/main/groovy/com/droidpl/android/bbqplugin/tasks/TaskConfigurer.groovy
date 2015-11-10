package com.droidpl.android.bbqplugin.tasks

import org.gradle.api.Project

/**
 * Abstract class that allows the creation of different tasks.
 */
public abstract class TaskConfigurer {

    /**
     * Checks the preconditions for the tasks.
     */
    public abstract void checkPreconditions();

    /**
     * Performs any action on the given project.
     * @param project The project.
     */
    public abstract void configureOn(Project project);
}
