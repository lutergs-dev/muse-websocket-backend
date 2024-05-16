package dev.lutergs.muse.websocket.infra.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "custom.main")
data class MainServerConfigProperties (
  val serverUrl: String
)