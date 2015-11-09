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
```
./gradlew -PpluginCompile bbqplugin:install
```

## Test the application
To install the application and make it work, you need to install the plugin first as stated in the 
previous section. After that you can execute one of the following commands to test the tasks
explained in the talk:

1. Continuous integration commit number counting.
2. Continuous integration build number.
3. Android tests reports from jacoco.
4. Unit testing reports from jacoco on JVM.
5. Code quality information upload.
6. Documentation from doclava.