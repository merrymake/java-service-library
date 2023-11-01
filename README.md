# Java Service Library for Merrymake

## Usage (Gradle)

1. In your `build.gradle` file, add this repository:

   ```groovy
   repositories {
       mavenCentral()
   }
   ```

1. Under dependencies, specify the artifact and version:

   ```groovy
   dependencies {
       ...
       implementation 'eu.merrymake.service.java:java-service-library:<version>'
   }
   ```

## Development

This library is built with the LTS version of the OpenJDK,
which is currently 17.0.2.

## Build

```shell
./gradlew build
```

## Testing Locally

To push a package to your local Maven repository, use the following command:

```shell
./gradlew clean build publishToMavenLocal
```

In another project,

1. add the repository,

   ```groovy
   repositories {
       mavenLocal()
       ...
   }
   ```

1. add the dependency:

   ```groovy
   dependencies {
       ...
       implementation 'eu.merrymake.service.java:java-service-template:0.1.0-SNAPSHOT'
   }
   ```

Where the version matches the one in this projects' `build.gradle`.
