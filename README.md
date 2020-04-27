# Google Artifact Registry SBT plugin
An SBT dependency resolver and publisher for
<a href="https://cloud.google.com/artifact-registry/">Google Artifact Registry</a> Maven repositories.

It is implemented as a wrapepr around
<a href="https://github.com/GoogleCloudPlatform/artifact-registry-maven-tools">Artifact Registry Maven Tools</a>.

## Usage

Add the following to `plugins.sbt` in your project:

`addSbtPlugin("ch.firsts" % "sbt-google-artifact-registry" % "1.0.0")`

To use this plugin as a resolver, add the following to build.sbt:

`resolvers += ArtifactRegistryResolver.forRepository("https://<REGION>-maven.pkg.dev/<PROJECT>/<REPOSITORY>")`

To use the plugin to publish artifacts:

`publishTo := Some(ArtifactRegistryResolver.forRepository("https://<REGION>-maven.pkg.dev/<PROJECT>/<REPOSITORY>"))`

## Prerequisites
- The specified Maven repository should already exist in the Google Artifact Registry.
- For development on local computer the user has to be logged-in into the gcloud command line tool with credentials
which have IAM permissions to read or write the Maven repository.

## Limitations
- Coursier is not supported. When using recent SBT versions where Coursier artifact resolution is enabled by default,
it has to be disabled by adding `useCoursier := false` to `build.sbt`, since Coursier partially uses it's own code to
resolve artifacts, which does not fully depend on the resolvers to download the artifacts.
- Publishing multiple SNAPSHOTS of the same version is not supported. The plugin uses Ivy IBiblioResolver as the base
implementation, which hardcodes maven metadata locations in a way that is not compatible with the SBT.
