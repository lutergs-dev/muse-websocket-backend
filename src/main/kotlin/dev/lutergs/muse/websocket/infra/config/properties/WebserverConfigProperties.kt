package dev.lutergs.muse.websocket.infra.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "custom.web")
class WebserverConfigProperties (
  hostname: String
) {
  val hostname = "muse-websocket-${hostname.takeLast(5)}"
}