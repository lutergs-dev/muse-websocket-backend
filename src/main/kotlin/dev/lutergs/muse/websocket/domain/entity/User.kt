package dev.lutergs.muse.websocket.domain.entity

import org.springframework.web.socket.WebSocketSession

data class User (
  val id: Long,
  val session: WebSocketSession
)