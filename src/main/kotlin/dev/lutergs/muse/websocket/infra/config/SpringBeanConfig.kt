package dev.lutergs.muse.websocket.infra.config

import dev.lutergs.muse.websocket.infra.UserAuthRepositoryImpl
import dev.lutergs.muse.websocket.infra.UserRepositoryImpl
import dev.lutergs.muse.websocket.infra.UserWebSocketSessionMap
import dev.lutergs.muse.websocket.infra.config.properties.MainServerConfigProperties
import dev.lutergs.muse.websocket.infra.config.properties.WebserverConfigProperties
import dev.lutergs.muse.websocket.infra.controller.WebsocketHandler
import dev.lutergs.muse.websocket.service.UserService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.StringRedisTemplate

@Configuration
@EnableConfigurationProperties(value = [
  MainServerConfigProperties::class,
  WebserverConfigProperties::class
])
class SpringBeanConfig(
  private val mainServerConfigProperties: MainServerConfigProperties,
  private val webserverConfigProperties: WebserverConfigProperties
) {

  @Bean
  fun connectedUserManager(
    stringRedisTemplate: StringRedisTemplate
  ): dev.lutergs.muse.websocket.infra.repository.redis.ConnectedUserManager =
    dev.lutergs.muse.websocket.infra.repository.redis.ConnectedUserManager(
      webserverConfigProperties = this.webserverConfigProperties,
      stringRedisTemplate = stringRedisTemplate
    )

  @Bean
  fun userWebSocketSessionMap(): UserWebSocketSessionMap = UserWebSocketSessionMap()

  @Bean
  fun userAuthRepositoryImpl(): UserAuthRepositoryImpl = UserAuthRepositoryImpl(
    mainServerConfigProperties = this.mainServerConfigProperties
  )

  @Bean
  fun userRepositoryImpl(
    connectedUserManager: dev.lutergs.muse.websocket.infra.repository.redis.ConnectedUserManager,
    userWebSocketSessionMap: UserWebSocketSessionMap
  ): UserRepositoryImpl = UserRepositoryImpl(
    connectedUserManager = connectedUserManager,
    userWebSocketSessionMap = userWebSocketSessionMap
  )

  @Bean
  fun userService(
    userAuthRepositoryImpl: UserAuthRepositoryImpl,
    userRepositoryImpl: UserRepositoryImpl
  ): UserService = UserService(
    userAuthRepository = userAuthRepositoryImpl,
    userRepository = userRepositoryImpl
  )

  @Bean
  fun websocketHandler(
    userService: UserService
  ): WebsocketHandler = WebsocketHandler(
    userService = userService
  )
}