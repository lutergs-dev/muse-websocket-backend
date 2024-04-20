package dev.lutergs.musewebsocketbackend.infra.websocket

import dev.lutergs.musewebsocketbackend.domain.repository.UserRepository
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

class TestWebSocketHandler(
  private val userRepository: UserRepository
): TextWebSocketHandler() {
  private val sessionIdAndUserMap = ConcurrentHashMap<String, Pair<Long, WebSocketSession>>()

  override fun afterConnectionEstablished(session: WebSocketSession) {
    session.attributes["token"]
      ?.let { it as String }
      ?.let { this.userRepository.getUserId(it) }
      ?.let { this.sessionIdAndUserMap.put(session.id, it to session) }
  }

  override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {

    // decrypt textmessage and do auth

    session.id

    super.handleTextMessage(session, message)
  }

  override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
    super.afterConnectionClosed(session, status)
  }
}