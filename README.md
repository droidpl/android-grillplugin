# BABBQGradle

Supporting example for the talk "The other Android Getting Started Guide" in the Big Android BBQ
This plugin enables the following features in an android project:
 * Continuous integration tips
 * Unit testing
 * Unit test code coverage: Jacoco
 * Code quality: Sonar
 * Documentation: Doclava

## Install the plugin

To install the plugin use the following command line:
```bash
./gradlew -PpluginCompile bbqplugin:install
```

## Apply plugin in your application
To apply a plugin in the application you can use:
```groovy
apply plugin: "bbqplugin"
```
You can use also the full name plugin:

```groovy
apply plugin: "com.droidpl.android.bbqplugin"
```

## Test the application
To install the application in your device, you need to install the plugin first as stated in the
previous section. After that you can execute one of the following commands to test the tasks
explained in the talk.

###Debug
1. Android tests reports with jacoco:
2. Unit testing reports with jacoco on JVM:
3. Code quality information uploaded:
4. Documentation from doclava:
5. Install the application with CI info: ```./gradlew installDebug/installDebug```

### Release
1. Android tests reports with jacoco:
2. Unit testing reports with jacoco on JVM:
3. Code quality information uploaded:
4. Documentation from doclava:
5. Install the application with CI info: ```./gradlew installDebug/installRelease```