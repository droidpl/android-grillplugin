package com.github.droidpl.android.grillplugin.utils
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.Project
/**
 * Helper class that performs some operations related to android.
 */
public class Utils {

    static def isAndroidPlugin(Project project){
        def isAndroidApp = project.plugins.withType(AppPlugin)
        def isAndroidLibrary = project.plugins.withType(LibraryPlugin)

        // Check that this plugin is being applied on an Android application or library
        return isAndroidApp || isAndroidLibrary
    }

    static def getVariants(Project project){
        if(project.plugins.withType(AppPlugin)){
            return project.android.applicationVariants
        }else if(project.plugins.withType(LibraryPlugin)){
            return project.android.libraryVariants
        }
        return null
    }

    static def buildVariantPath(BaseVariant variant) {
        StringBuilder builder = new StringBuilder()
        if(variant.getFlavorName() != null){
            builder.append("/${variant.getFlavorName()}")
        }
        builder.append("/${variant.getBuildType().getName()}")
        return builder.toString()
    }
}
