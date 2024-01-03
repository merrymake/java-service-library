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

### Publish to Maven Repositories (Snapshot/Staging)

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

Continue down below with either "Testing a Snapshot" or "Making a Release."

## Publish to MavenCentral

Visit <https://s01.oss.sonatype.org/#welcome> and log in.

Follow the guide on <https://central.sonatype.org/publish/release/>

### Testing a Snapshot

You can do this after publishing a SNAPSHOT.

1. Locate the snapshot in
   "Views/Repositories" -> "Repositories" -> "Snapshots"
   under e.g. `eu/merrymake/service/..`

   If you select the `.jar` you should be able to see the XML
   for consuming the dependency, e.g.

   ```xml
   <dependency>
   <groupId>eu.merrymake.service.java</groupId>
   <artifactId>java-service-library</artifactId>
   <version>1.0.0-SNAPSHOT</version>
   </dependency>
   ```

1. Add it to your dependencies, e.g. in gradle:

   ```gradle
   implementation 'eu.merrymake.service.java:java-service-library:1.0.0-SNAPSHOT'
   ```

1. Also make sure the snapshot repository is enabled, e.g. in gradle:

   (if you want to see it fail and then pass, wait with this step.)

   ```gradle
   repositories {
      maven {
         url "https://s01.oss.sonatype.org/content/repositories/snapshots/"
      }
   }
   ```

You're now good to go.

> NB: a snapshot can be deleted by right clicking in the browser
> and choosing delete.

### Making a Release

These can not be deleted.

You can do this after

- publishing to Staging, and
- making sure your signatures are publicly available, see guide above.

1. Navigate to "Build Promotion" -> "Staging Repositories,"
   hit "refresh".

   It may take a minute.. or 30, to show up.

1. Select one of the staging-repositories, created for you.
   Verify the artifact under "Content."

   - it must contain `.asc` and `.sha512` files.

1. Choose "Close" if the contents are good.
   Wait for the automation to complete. This takes ~5-30 minutes.

## Troubleshooting

### Missing artifacts

When browsing the "Content" of an uploaded artifact,
it may be missing `.asc` or checksum-files like `.sha512`.

This is annoying. You just need to drop these repositories and publish again:

```shell
$ ./gradlew clean publishMavenJavaToMavenRepository
```

The staging repositories are often under a lot of pressure.

### Signing

If you want to run the `signing`-task manually, this can be done with

```shell
$ ./gradlew signMavenJavaPublication
```
