package com.droidpl.android.bbqplugin
/**
 * Plugin extension to allow configurations on the current plugin.
 */
public class CIHelper {

    public int getCommitRevision(){
        def counter = 0
        def process = "git rev-list HEAD --first-parent --count".execute()
        def text = process.text
        if(!text.isEmpty()){
            counter = text.toInteger()
        }
        return counter
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
