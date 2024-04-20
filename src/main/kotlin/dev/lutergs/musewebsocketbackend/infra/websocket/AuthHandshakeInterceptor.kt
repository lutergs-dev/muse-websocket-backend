package dev.lutergs.musewebsocketbackend.infra.websocket

import org.springframework.http.HttpStatus
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import java.lang.Exception

class AuthHandshakeInterceptor: HandshakeInterceptor {
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