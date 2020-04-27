package ch.firsts.sbt.gar

import java.io.File
import java.util

import com.google.cloud.artifactregistry.wagon.ArtifactRegistryWagon
import org.apache.ivy.core.module.descriptor.Artifact
import org.apache.ivy.plugins.repository.AbstractRepository
import org.apache.maven.wagon.repository.Repository

class ArtifactRegistryRepository(repositoryUrl: String) extends AbstractRepository {
  val repo = new Repository("google-artifact-registry", repositoryUrl)
  val wagon = new ArtifactRegistryWagon()

  override def getResource(source: String): ArtifactRegistryResource = {
    val plainSource = stripRepository(source)
    wagon.connect(repo)
    ArtifactRegistryResource(repositoryUrl, plainSource, wagon.resourceExists(plainSource))
  }

  override def get(source: String, destination: File): Unit = {
    val adjustedSource = if (destination.toString.endsWith("sha1"))
      source + ".sha1"
    else if (destination.toString.endsWith("md5"))
      source + ".md5"
    else
      source

    wagon.connect(repo)
    wagon.get(adjustedSource, destination)
  }

  override def list(parent: String): util.List[String] = sys.error("Listing repository contents is not supported")

  override def put(artifact: Artifact, source: File, destination: String, overwrite: Boolean): Unit = {
    val plainDestination = stripRepository(destination)
    wagon.connect(repo)
    wagon.put(source, plainDestination)
  }

  private def stripRepository(fullName: String): String = fullName.substring(repositoryUrl.length + 1)

}
