package com.github.droidpl.android.grillplugin.utils
/**
 * Plugin extension to allow configurations on the current plugin.
 */
public class CIHelper {

    private final String COMMAND = "git rev-list HEAD --first-parent --count"

    public int getCommitRevision(){
        def count = 0
        def commitNumber = COMMAND.execute().text
        if(!commitNumber.isEmpty()){
            count = commitNumber.toInteger()
        }
        return count
    }

    public String getTag(){
        def tag = "git describe".execute().text.trim()
        if(tag.isEmpty()){
            tag = "v0"
        }
        return tag
    }

    public String getBranch(){
        return "git symbolic-ref --short -q HEAD".execute().text.trim();
    }
}
