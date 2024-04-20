package dev.lutergs.musewebsocketbackend.domain.entity

import java.time.Instant

data class NowPlaying(
  val track: Track,
  val status: PlaybackStatus,
  val timestamp: Long
)