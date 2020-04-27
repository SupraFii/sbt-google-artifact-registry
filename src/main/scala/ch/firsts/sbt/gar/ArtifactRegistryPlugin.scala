package ch.firsts.sbt.gar

import sbt._

object ArtifactRegistryPlugin extends AutoPlugin {

  object autoImport {
    val ArtifactRegistryResolver = ch.firsts.sbt.gar.ArtifactRegistryResolver
  }

  override def trigger: PluginTrigger = allRequirements

}
