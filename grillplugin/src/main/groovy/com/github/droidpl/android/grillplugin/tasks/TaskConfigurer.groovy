package com.github.droidpl.android.grillplugin.tasks

import org.gradle.api.Project

/**
 * Abstract class that allows the creation of different tasks.
 */
public interface TaskConfigurer {

    /**
     * Checks the preconditions for the tasks.
     */
    void checkPreconditions()

    /**
     * Performs any action on the given project.
     * @param project The project.
     */
    void configureOn(Project project)

    /**
     * Checks if the task is enabled.
     * @return True if enabled, false otherwise.
     */
    boolean isEnabled()
}
