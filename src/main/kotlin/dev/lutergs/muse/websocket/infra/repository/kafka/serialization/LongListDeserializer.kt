package dev.lutergs.muse.websocket.infra.repository.kafka.serialization

import org.apache.kafka.common.serialization.Deserializer

class LongListDeserializer: Deserializer<List<Long>> {
  override fun deserialize(topic: String, data: ByteArray): List<Long> {
    return data.toString(Charsets.UTF_8)
      .drop(1)
      .dropLast(1)
      .split(",").map { it.trim().toLong() }
  }
}