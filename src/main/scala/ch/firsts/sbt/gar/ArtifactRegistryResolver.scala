package ch.firsts.sbt.gar

import java.util.Collections

import org.apache.ivy.plugins.resolver.IBiblioResolver

import scala.language.implicitConversions

class ArtifactRegistryResolver(resolverName: String, repositoryUrl: String) extends IBiblioResolver {
  private val fullPattern = repositoryUrl + "/" + ArtifactRegistryResolver.Pattern
  setName(resolverName)
  setRoot(repositoryUrl)
  setRepository(new ArtifactRegistryRepository(repositoryUrl))
  setM2compatible(true)
  setUseMavenMetadata(false) // IBiblioResolver uses hardcoded metadata location, which does not match the pattern used
  setArtifactPatterns(Collections.singletonList(fullPattern))
  setIvyPatterns(Collections.singletonList(fullPattern))
}

object ArtifactRegistryResolver {
  val Pattern = "[organisation]/[module](_[scalaVersion])(_[sbtVersion])/[revision]/[artifact](_[scalaVersion])(_[sbtVersion])-[revision](-[classifier]).[ext]"

  implicit def toSbtResolver(resolver: ArtifactRegistryResolver): sbt.Resolver = {
    new sbt.RawRepository(resolver, resolver.getName)
  }

  def forRepository(repositoryUrl: String): ArtifactRegistryResolver = {
    new ArtifactRegistryResolver("google-artifact-registry", repositoryUrl)
  }
}
