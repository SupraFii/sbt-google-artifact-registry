package ch.firsts.sbt.gar

import java.io.InputStream

import org.apache.ivy.plugins.repository.Resource

case class ArtifactRegistryResource(repositoryUrl: String, artifactName: String, exists: Boolean) extends Resource {
  override def getName: String = artifactName
  override def getLastModified: Long = 0
  override def getContentLength: Long = 0
  override def openStream(): InputStream = sys.error("Streaming resources is not supported")
  override def isLocal: Boolean = false
  override def clone(cloneName: String): Resource = copy()
  override def toString: String = s"$repositoryUrl/$artifactName"
}
