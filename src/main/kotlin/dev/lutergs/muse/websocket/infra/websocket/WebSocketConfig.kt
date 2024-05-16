package dev.lutergs.muse.websocket.infra.websocket

import dev.lutergs.muse.websocket.infra.controller.WebsocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig(
  private val websocketHandler: WebsocketHandler
) : WebSocketConfigurer {
  override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
    registry.addHandler(this.websocketHandler, "/ws")
      .setAllowedOrigins("*") // CORS 정책에 따라 변경할 수 있습니다.
  }
}