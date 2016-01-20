![Android plugin](https://img.shields.io/badge/Android%20plugin-active-green.svg)
![Gradle](https://img.shields.io/badge/Gradle-compatible-brightgreen.svg)
[![Download](https://api.bintray.com/packages/droidpl/maven/GrillPlugin/images/download.svg) ](https://bintray.com/droidpl/maven/GrillPlugin/_latestVersion)

# Grill plugin
Android Gradle plugin based on the talk "The other Android Getting Started Guide" in the Big Android BBQ (Europe).
This plugin enables the following features in your android project:
 * Some continuous integration tips
 * Unit testing
 * Unit test code coverage: Jacoco
 * Code quality: Sonar
 * Documentation: Doclava
 * Library distribution in JCenter and MavenCentral

## How to use it
Add the classpath dependency:
```groovy
buildscript {
    repositories {
        maven { url "https://plugins.gradle.org/m2/" }
    }
    dependencies {
        classpath 'com.github.droidpl:grillplugin:{version}'
    }
}
```
Apply a plugin in the application or library:
```groovy
apply plugin: "grill"
```
# DSL

```groovy
grill {
        debug [Boolean]
        codeQuality {
            host [String Url]
            projectKey [String]
            projectName [String]
            projectVersion [String version]
            dbHost [String Url]
            dbUser [String]
            dbPassword [String]
        }
        documentation {
            templateDir [String]
            doclavaVersion [String version]
        }
        coverage {}
        googlePlay {
            privateKeyId [String]
            clientEmail [String]
            privateKey [File (.pem file in your system]
            customize { editor [AppEdit], editId [String], apk [Apk]
                // find more doc here: http://jeremie-martinez.com/2016/01/14/devops-on-android/
            }
        }
        disribute {
            libVersion [String] (Optional if in project.version)
            libPackaging [String] (Optional)
            libGroupId [String] (Optional if in project.group)
            libArtifact [String] (Optional)
            libName [String]
            libDescription [String] (Optional)
            libLicName [String] (Optional: default apache v2)
            libLicUrl [String] (Optional: default apache v2)
            libDevId [String] (Optional)
            libDevName [String] (Optional)
            libDevEmail [String] (Optional)
            libSiteUrl [String] (Optional)
            bintrayRepoName [String]
            bintrayUser [String]
            bintrayKey [String]
            bintrayRepo [String] (Optional: default maven)
            bintrayMavenCentralUser [String] (Optional)
            bintrayMavenCentralPassword [String] (Optional)
            bintrayLicenses [String[]] (Optional)
            bintrayArtifactJavadoc [Task]
            bintrayArtifactSources [Task]
            bintrayMavenSync [Boolean]
            binrayGpgSign [Boolean]
            bintrayGpgPassphrase [String]
        }
    }
```

# Development
## Install the plugin
To install the plugin use the following command line:
```bash
./gradlew -PpluginCompile grillplugin:install
```

## Test the application
To install the application in your device, you need to install the plugin first as stated in the
previous section. After that you can execute one of the following commands to test the tasks
explained in the talk.

### Debug
1. Unit testing reports with jacoco on JVM: ```createDebugUnitTestCoverageReport```
2. Code quality information uploaded: ```codeQualityDebug```
3. Documentation from doclava: ```documentationDebug```
4. Google play publishing: ```googlePlayPublishDebug```
5. Install the application with CI info: ```installDebug```

### Release
1. Unit testing reports with jacoco on JVM: ```createReleaseUnitTestCoverageReport```
2. Code quality information uploaded: ```codeQualityRelease```
3. Documentation from doclava: ```documentationRelease```
4. Google play publishing: ```googlePlayPublishRelease```
5. Install the application with CI info: ```./gradlew installRelease```
