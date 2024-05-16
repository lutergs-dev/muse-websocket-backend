package dev.lutergs.muse.websocket.infra.repository.kafka.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import dev.lutergs.muse.websocket.infra.repository.kafka.NotifierListDto
import org.apache.kafka.common.serialization.Deserializer

class ProduceValueDtoDeserializer: Deserializer<NotifierListDto> {
  private val objectMapper = ObjectMapper()

  override fun deserialize(topic: String, data: ByteArray): NotifierListDto {
    return this.objectMapper.readTree(data)
      .let { node ->
        NotifierListDto(
          userIds = node.get("userIds").map { it.asLong() },
          friendId = node.get("friendId").asLong()
        )
      }
  }
}