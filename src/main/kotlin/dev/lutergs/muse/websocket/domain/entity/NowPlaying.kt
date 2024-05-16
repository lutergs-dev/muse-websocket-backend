package dev.lutergs.muse.websocket.domain.entity


data class NowPlaying(
  val track: Track,
  val status: PlaybackStatus,
  val timestamp: Long
) {
  fun toWebsocketMessage(friendId: Long): String {
    return "f:${friendId}|v:${this.track.vendor.name}|u:${this.track.uid}|s:${this.status.name}"
  }
}