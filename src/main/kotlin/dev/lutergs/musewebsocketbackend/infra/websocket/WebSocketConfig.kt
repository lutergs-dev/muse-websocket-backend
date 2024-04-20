package dev.lutergs.musewebsocketbackend.infra.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig(
  private val testWebSocketHandler: TestWebSocketHandler,
  private val authHandshakeInterceptor: AuthHandshakeInterceptor
) : WebSocketConfigurer {
  override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
    registry.addHandler(this.testWebSocketHandler, "/ws")
      .addInterceptors(authHandshakeInterceptor)
      .setAllowedOrigins("*") // CORS 정책에 따라 변경할 수 있습니다.
  }
}