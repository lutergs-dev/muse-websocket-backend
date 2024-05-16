package dev.lutergs.muse.websocket.infra.config

import dev.lutergs.muse.websocket.infra.config.properties.KafkaProxyConfigProperties
import dev.lutergs.muse.websocket.infra.config.properties.WebserverConfigProperties
import dev.lutergs.muse.websocket.infra.controller.KafkaReceiver
import dev.lutergs.muse.websocket.infra.repository.kafka.proxy.KafkaRestProxyClient
import dev.lutergs.muse.websocket.service.UserService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [
  KafkaProxyConfigProperties::class
])
class KafkaBeanConfig(
  private val kafkaProxyConfigProperties: KafkaProxyConfigProperties,
  private val webserverConfigProperties: WebserverConfigProperties
) {
  @Bean
  fun kafkaRestProxyClient(): KafkaRestProxyClient = KafkaRestProxyClient(
    kafkaProxyConfigProperties = kafkaProxyConfigProperties
  )

  @Bean(name = ["kafkaTopicName"])
  fun kafkaTopicName(): String = this.webserverConfigProperties.hostname

  @Bean
  fun kafkaReceiver(
    webserverConfigProperties: WebserverConfigProperties,
    userService: UserService,
    kafkaRestProxyClient: KafkaRestProxyClient,
  ): KafkaReceiver = KafkaReceiver(
    webserverConfigProperties = webserverConfigProperties,
    userService = userService,
    kafkaRestProxyClient = kafkaRestProxyClient
  )
}