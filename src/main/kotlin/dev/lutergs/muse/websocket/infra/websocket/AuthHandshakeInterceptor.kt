package dev.lutergs.muse.websocket.infra.websocket

import dev.lutergs.muse.websocket.domain.repository.UserAuthRepository
import org.springframework.http.HttpStatus
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import java.lang.Exception

@Deprecated(message = "아직 사용 용례를 찾지 못했음")
class AuthHandshakeInterceptor(
  private val userAuthRepository: UserAuthRepository
): HandshakeInterceptor {
  override fun beforeHandshake(
    request: ServerHttpRequest,
    response: ServerHttpResponse,
    wsHandler: WebSocketHandler,
    attributes: MutableMap<String, Any>
  ): Boolean {

    return request.headers["Authorization"]
      ?.let { token -> attributes["token"] = token; true }
      ?: run {response.setStatusCode(HttpStatus.UNAUTHORIZED); false }
  }

  override fun afterHandshake(
    request: ServerHttpRequest,
    response: ServerHttpResponse,
    wsHandler: WebSocketHandler,
    exception: Exception?
  ) {
    // Not Implemented
  }
}