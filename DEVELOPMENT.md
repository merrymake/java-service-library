# Development notes

## Testing

Tests can be run using the `test`-gradle target,

```shell
$ ./gradlew test
# you may want to "clean and build" before testing, if you haven't already.
$ ./gradlew clean build test
```

## Releasing

### Prerequisites

Create a `gradle.properties`-file in your home directory,
and add your gpg settings here.

```shell
$ cat ~/.gradle/gradle.properties
signing.keyId=<key-id>
signing.password=<passphrase>
signing.secretKeyRingFile=<path to keyring>
```

Information on this can be found on either gradle-docs,
or <https://central.sonatype.org/publish/publish-gradle/#credentials>

### Publish to Maven Repositories

1. Export credentials for our Maven repository

   ```shell
   $ export CENTRAL_TOKEN=<token>
   $ export CENTRAL_USER=<username>
   ```

1. Update the version of the artifact by editing the
   `version = '1.0.0-SNAPSHOT'` in the `/lib/build.gradle` file.

   If it ends with "SNAPSHOT" it's deployed to the test/snapshot repo.

1. Publish with:

   ```shell
   $ ./gradlew clean build publishMavenJavaToMavenRepository
   ```

### Publish to MavenCentral

Visit <https://s01.oss.sonatype.org/#welcome> and log in.

Follow the guide on <https://central.sonatype.org/publish/release/>
