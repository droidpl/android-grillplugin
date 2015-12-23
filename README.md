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
    dependencies {
        classpath 'com.github.droidpl.android:grillplugin:1.0'
    }
}
```
Apply a plugin in the application or library:
```groovy
apply plugin: "grill"
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

###Debug
1. Unit testing reports with jacoco on JVM: ```createDebugUnitTestCoverageReport```
2. Code quality information uploaded: ```codeQualityDebug```
3. Documentation from doclava: ```documentationDebug```
4. Install the application with CI info: ```installDebug```

### Release
1. Unit testing reports with jacoco on JVM: ```createReleaseUnitTestCoverageReport```
2. Code quality information uploaded: ```codeQualityRelease```
3. Documentation from doclava: ```documentationRelease```
4. Install the application with CI info: ```./gradlew installRelease```