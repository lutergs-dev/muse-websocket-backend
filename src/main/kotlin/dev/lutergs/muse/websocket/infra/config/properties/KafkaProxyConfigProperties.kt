package dev.lutergs.muse.websocket.infra.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.web.client.RestClient
import java.util.Base64

@ConfigurationProperties(prefix = "custom.kafka.proxy")
data class KafkaProxyConfigProperties (
  val url: String,
  val clusterId: String,
  val apiKey: String,
  val apiSecret: String
) {

  fun buildRestClient(): RestClient {
    return RestClient.builder()
      .baseUrl("${this.url}/kafka/v3/clusters/${this.clusterId}")
      .defaultHeader(
        "Authorization",
        Base64.getEncoder()
          .encodeToString("${this.apiKey}:${this.apiSecret}".toByteArray(Charsets.UTF_8))
          .let { "Basic $it" }
      ).defaultHeader("Content-Type", "application/json")
      .build()
  }
}