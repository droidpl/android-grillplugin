package com.droidpl.android.grillplugin.tasks
/**
 * Abstract class that allows the creation of different tasks.
 */
public abstract class AbstractTaskConfigurer implements TaskConfigurer {

    private boolean enabled = true

    public void enabled(boolean enabled){
        this.enabled = enabled
    }

    @Override
    public final boolean isEnabled() {
        return enabled
    }
}
