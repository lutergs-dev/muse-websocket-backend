package dev.lutergs.muse.websocket.infra.controller

import dev.lutergs.muse.websocket.domain.entity.User
import dev.lutergs.muse.websocket.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class WebsocketHandler(
  private val userService: UserService,
): TextWebSocketHandler() {
  private val logger = LoggerFactory.getLogger(WebsocketHandler::class.java)

  override fun afterConnectionEstablished(session: WebSocketSession) {
    this.getUserAuthorizationToken(session)
      ?.let { token ->
        this.userService.getUserIdFromToken(token)
          ?.let { userId -> this.userService.saveUser(User(userId, session))}
          ?: session.close(CloseStatus.NOT_ACCEPTABLE.withReason("invalid credential"))
      }
      ?: session.close(CloseStatus.BAD_DATA.withReason("missing credential"))
  }

  override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
    // 기타 input 이 들어왔을 땐, 해당 연결을 끊음
    this.getUserAuthorizationToken(session)
      ?.let { token ->
        this.userService.getUserIdFromToken(token)
          ?.let { userId -> this.userService.removeUser(userId) }
      }
    session.close(CloseStatus.POLICY_VIOLATION.withReason("send data to server is not permitted"))
  }

  override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
    this.getUserAuthorizationToken(session)
      ?.let { token ->
        this.userService.getUserIdFromToken(token)
          ?.let { userId -> this.userService.removeUser(userId) }
      }
  }

  private fun getUserAuthorizationToken(session: WebSocketSession): String? {
    return session.handshakeHeaders["Authorization"]
      ?.first()
      ?.let { it.split(" ")[1] }
  }
}