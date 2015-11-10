package com.droidpl.android.grillplugin.tasks

import com.droidpl.android.grillplugin.utils.Utils
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException
import org.gradle.sonar.runner.tasks.SonarRunner

/**
 * Task to allow the sonar runner to make the work.
 */
public class SonarQualityTask extends TaskConfigurer{

    /**
     * Sonar fully qualified host name.
     */
    private String sonarHost
    /**
     * The project key that has been set in sonar.
     */
    private String sonarProjectKey
    /**
     * The project name from sonar.
     */
    private String sonarProjectName
    /**
     * The database host where sonar is taking all the data.
     */
    private String databaseHost
    /**
     * The database driver. By default it is the mysql.
     */
    private String databaseDriver = "com.mysql.jdbc.Driver"
    /**
     * Database username.
     */
    private String databaseUsername
    /**
     * Database password.
     */
    private String databasePassword


    /**
     * The intermediates for the plugin.
     */
    //TODO fix this name
    private final String BINARIES = "/intermediates/classes"
    /**
     * The path for jacoco info.
     */
    private final String JACOCO_PATH = "/jacoco"

    public SonarQualityTask(Project project){
        sonarProjectName = project.name
    }

    def host(String hostName){
        sonarHost = hostName
    }

    def projectName(String projectName){
        sonarProjectName = projectName
    }

    def projectKey(String projectKey){
        sonarProjectKey = projectKey
    }

    def dbHost(String host){
        databaseHost = host
    }

    def dbDriver(String driverClass){
        databaseDriver = driverClass
    }

    def dbUser(String username){
        databaseUsername = username
    }

    def dbPassword(String password){
        databasePassword = password
    }

    @Override
    void checkPreconditions() {
        if(sonarHost == null || sonarProjectKey == null || databaseHost == null || databaseUsername == null || databasePassword == null){
            throw new ProjectConfigurationException("The project could not be configured because one of the fields from the code quality task was not set.", null)
        }
    }

    @Override
    void configureOn(Project project) {
        project.apply plugin : "sonar-runner"
        Utils.getVariants(project).all { variant ->
            project.tasks.create(name: "codeQuality${variant.getName().capitalize()}", type: SonarRunner, dependsOn: "test${variant.getName().capitalize()}UnitTest") {
                group = "BBQ"
                project.sonarRunner {
                    sonarProperties {
                        property "sonar.host.url", "$sonarHost"
                        property "sonar.jdbc.url", "$databaseHost"
                        property "sonar.jdbc.driverClassName", "$databaseDriver"
                        property "sonar.jdbc.username", "$databaseUsername"
                        property "sonar.jdbc.password", "$databasePassword"

                        property "sonar.projectKey", "$sonarProjectKey"
                        property "sonar.projectName", "$sonarProjectName"

                        property "sonar.sources", "$SOURCES"
                        property "sonar.binaries", "${project.buildDir}$BINARIES"
                        property "sonar.jacoco.reportPath", "${project.buildDir}$JACOCO_PATH/test${variant.getName().capitalize()}UnitTest.exec"
                    }
                }
            }
        }
    }
}
