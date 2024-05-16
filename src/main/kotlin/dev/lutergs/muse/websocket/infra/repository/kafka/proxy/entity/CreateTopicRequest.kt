package dev.lutergs.muse.websocket.infra.repository.kafka.proxy.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.kafka.clients.admin.NewTopic
import java.util.*


data class CreateTopicRequest(
  @JsonProperty("topic_name")           val topicName: String,
  @JsonProperty("partitions_count")     val partitions: Int,
  @JsonProperty("replication_factor")   val replicationFactor: Int,
  @JsonProperty("configs")              val configs: List<CreateTopicRequestProperties>
) {
  companion object {
    fun fromNewTopic(newTopic: NewTopic): CreateTopicRequest {
      return CreateTopicRequest(
        topicName = newTopic.name(),
        partitions = newTopic.numPartitions(),
        replicationFactor = newTopic.replicationFactor().toInt(),
        configs =
          Optional.ofNullable(newTopic.configs())
            .map { configs -> configs.map { CreateTopicRequestProperties.fromMapEntry(it) } }
            .orElse(listOf())
      )
    }
  }
}


data class CreateTopicRequestProperties(
  @JsonProperty("name")   val name: String,
  @JsonProperty("value")  val value: String
) {
  companion object {
    fun fromMapEntry(entry: Map.Entry<String, String>): CreateTopicRequestProperties {
      return CreateTopicRequestProperties(
        name = entry.key,
        value = entry.value
      )
    }
  }
}