package dev.lutergs.muse.websocket.infra.repository.kafka.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import dev.lutergs.muse.websocket.domain.entity.MusicVendor
import dev.lutergs.muse.websocket.domain.entity.NowPlaying
import dev.lutergs.muse.websocket.domain.entity.PlaybackStatus
import dev.lutergs.muse.websocket.domain.entity.Track
import org.apache.kafka.common.serialization.Deserializer

class NowPlayingDeserializer: Deserializer<NowPlaying> {
  private val objectMapper = ObjectMapper()

  override fun deserialize(topic: String, data: ByteArray): NowPlaying {
    return this.objectMapper.readTree(data)
      .let { rootNode ->
        NowPlaying(
          track = rootNode.get("track").let { trackNode ->
            Track(
              vendor = MusicVendor.valueOf(trackNode.get("vendor").asText()),
              uid = trackNode.get("uid").asText()
            )
          },
          status = PlaybackStatus.valueOf(rootNode.get("status").asText()),
          timestamp = rootNode.get("timestamp").asLong()
        )
      }
  }
}