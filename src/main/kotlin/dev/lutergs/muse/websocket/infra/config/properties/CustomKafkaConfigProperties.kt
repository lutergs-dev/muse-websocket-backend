package dev.lutergs.muse.websocket.infra.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "custom.kafka")
data class CustomKafkaConfigProperties (
  val clientId: String,
  val clusterType: KafkaClusterType
) {
  val replicationFactor = when (this.clusterType) {
    KafkaClusterType.CONFLUENT -> 3
    KafkaClusterType.SELF_HOSTED -> 1
  }
}

enum class KafkaClusterType {
  CONFLUENT, SELF_HOSTED;
}