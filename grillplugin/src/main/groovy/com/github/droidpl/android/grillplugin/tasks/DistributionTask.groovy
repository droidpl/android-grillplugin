package com.github.droidpl.android.grillplugin.tasks

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.ProjectConfigurationException

public class DistributionTask extends AbstractTaskConfigurer {

    private String libVersion
    private String libPackaging = 'jar'
    private String libGroupId
    private String libArtifact // Project name
    private String libName
    private String libDescription = ""
    private String libLicName = 'The Apache Software License, Version 2.0'
    private String libLicUrl = 'http://www.apache.org/licenses/LICENSE-2.0.txt'
    private String libDevId = ""
    private String libDevName = ""
    private String libDevEmail = ""
    private String libSiteUrl = ""
    private String libGitUrl = ""

    private String bintrayRepoName
    private String bintrayUser
    private String bintrayKey
    private String bintrayRepo = 'maven'
    private String bintrayMavenCentralUser
    private String bintrayMavenCentralPassword
    private String bintrayLicenses = ["Apache-2.0"]
    private Task bintrayArtifactJavadoc
    private Task bintrayArtifactSources
    private boolean bintrayMavenSync = false
    private boolean bintrayPublishStats = true
    private boolean bintrayGpgSign = true
    private String bintrayGpgPassphrase = null

    void libVersion(String libVersion) {
        this.libVersion = libVersion
    }

    void libPackaging(String libPackaging) {
        this.libPackaging = libPackaging
    }

    void libGroupId(String libGroupId) {
        this.libGroupId = libGroupId
    }

    void libArtifact(String libArtifact) {
        this.libArtifact = libArtifact
    }

    void libName(String libName) {
        this.libName = libName
    }

    void libDescription(String libDescription) {
        this.libDescription = libDescription
    }

    void libLicName(String libLicName) {
        this.libLicName = libLicName
    }

    void libLicUrl(String libLicUrl) {
        this.libLicUrl = libLicUrl
    }

    void libDevId(String libDevId) {
        this.libDevId = libDevId
    }

    void libDevName(String libDevName) {
        this.libDevName = libDevName
    }

    void libDevEmail(String libDevEmail) {
        this.libDevEmail = libDevEmail
    }

    void libSiteUrl(String libSiteUrl) {
        this.libSiteUrl = libSiteUrl
    }

    void libGitUrl(String libGitUrl) {
        this.libGitUrl = libGitUrl
    }

    void bintrayRepoName(String bintrayName) {
        this.bintrayRepoName = bintrayName
    }

    void bintrayUser(String bintrayUser) {
        this.bintrayUser = bintrayUser
    }

    void bintrayKey(String bintrayKey) {
        this.bintrayKey = bintrayKey
    }

    void bintrayRepo(String bintrayRepo) {
        this.bintrayRepo = bintrayRepo
    }

    void bintrayMavenCentralUser(String bintrayMavenCentralUser) {
        this.bintrayMavenCentralUser = bintrayMavenCentralUser
    }

    void bintrayLicenses(String[] licenses) {
        this.bintrayLicenses = licenses
    }

    void bintrayMavenCentralPassword(String bintrayMavenCentralPassword) {
        this.bintrayMavenCentralPassword = bintrayMavenCentralPassword
    }

    void bintrayArtifactJavadoc(Task bintrayArtifactJavadoc) {
        this.bintrayArtifactJavadoc = bintrayArtifactJavadoc
    }

    void bintrayArtifactSources(Task bintrayArtifactSources) {
        this.bintrayArtifactSources = bintrayArtifactSources
    }

    void bintrayMavenSync(boolean bintrayMavenSync) {
        this.bintrayMavenSync = bintrayMavenSync
    }

    void bintrayPublishStats(boolean bintrayPublishStats) {
        this.bintrayPublishStats = bintrayPublishStats
    }

    void bintrayGpgSign(boolean bintrayGpgSign) {
        this.bintrayGpgSign = bintrayGpgSign
    }

    void bintrayGpgPassphrase(String bintrayGpgPassphrase) {
        this.bintrayGpgPassphrase = bintrayGpgPassphrase
    }

    @Override
    void checkPreconditions() {
        String property = null
        property = bintrayArtifactSources == null ? "bintrayArtifactSources" : property
        property = bintrayArtifactJavadoc == null ? "bintrayArtifactJavadoc" : property
        property = bintrayLicenses == null ? "bintrayLicenses" : property
        property = bintrayRepo == null ? "bintrayRepo" : property
        property = bintrayRepoName == null ? "bintrayRepoName" : property
        property = libGitUrl == null ? "libGitUrl" : property
        property = libSiteUrl == null ? "libSiteUrl" : property
        property = libDevEmail == null ? "libDevEmail" : property
        property = libDevName == null ? "libDevName" : property
        property = libDevId == null ? "libDevId" : property
        property = libLicUrl == null ? "libLicUrl" : property
        property = libLicName == null ? "libLicName" : property
        property = libDescription == null ? "libDescription" : property
        property = libName == null ? "libName" : property
        property = libPackaging == null ? "libPackaging" : property
        if(property != null) {
            throw new ProjectConfigurationException("Error while configuring the distribution. ${property} is null", null)
        }
    }

    @Override
    void configureOn(Project project) {
        if(libArtifact == null){
            libArtifact = project.name
        }
        if(libGroupId == null){
            libGroupId = project.group
        }
        if(libVersion == null){
            libVersion = project.version
        }
        //Apply plugins
        project.apply plugin: 'com.jfrog.bintray'
        project.apply plugin: 'com.github.dcendents.android-maven'
        project.group = libGroupId
        project.version = libVersion
        project.install {
            repositories.mavenInstaller {
                pom.project {
                    packaging libPackaging
                    groupId libGroupId
                    artifactId libArtifact

                    name libName
                    description libDescription
                    url libSiteUrl

                    // Set your license
                    licenses {
                        license {
                            name libLicName
                            url libLicUrl
                        }
                    }
                    developers {
                        developer {
                            id libDevId
                            name libDevName
                            email libDevEmail
                        }
                    }
                    scm {
                        connection libGitUrl
                        developerConnection libGitUrl
                        url libSiteUrl
                    }
                }
            }
        }
        project.artifacts {
            archives bintrayArtifactJavadoc
            archives bintrayArtifactSources
        }

        project.bintray {
            user = bintrayUser
            key = bintrayKey

            configurations = ['archives']
            pkg {
                repo = bintrayRepo
                name = bintrayRepoName
                desc = libDescription
                websiteUrl = libSiteUrl
                vcsUrl = libGitUrl
                licenses = bintrayLicenses
                publish = bintrayMavenSync
                publicDownloadNumbers = bintrayPublishStats
                version {
                    desc = libDescription
                    gpg {
                        sign = bintrayGpgSign //Determines whether to GPG sign the files. The default is false
                        passphrase = bintrayGpgPassphrase
                    }
                    mavenCentralSync {
                        sync = bintrayMavenSync
                        user = bintrayMavenCentralUser
                        password = bintrayMavenCentralPassword
                        close = bintrayMavenSync ? "1" : "0"
                    }
                }
            }
        }

        def oldTask = project.tasks.bintrayUpload
        project.tasks.remove(oldTask)
        oldTask.name = "distribute"
        oldTask.group = "grill"
        project.tasks.add(oldTask)
    }
}
