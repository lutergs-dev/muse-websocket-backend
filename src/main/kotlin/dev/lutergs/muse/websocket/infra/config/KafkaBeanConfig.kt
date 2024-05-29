package dev.lutergs.muse.websocket.infra.config

import dev.lutergs.muse.websocket.infra.config.properties.CustomKafkaConfigProperties
import dev.lutergs.muse.websocket.infra.config.properties.WebserverConfigProperties
import dev.lutergs.muse.websocket.infra.controller.KafkaReceiver
import dev.lutergs.muse.websocket.service.UserService
import org.apache.kafka.clients.admin.Admin
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Properties

@Configuration
@EnableConfigurationProperties(value = [
  CustomKafkaConfigProperties::class
])
class KafkaBeanConfig(
  private val customKafkaConfigProperties: CustomKafkaConfigProperties,
  private val webserverConfigProperties: WebserverConfigProperties,
  private val kafkaProperties: KafkaProperties
) {

  @Bean(name = ["admin"])
  fun kafkaAdmin(): Admin {
    val adminProperties = Properties().apply {
      this["bootstrap.servers"] = kafkaProperties.bootstrapServers
      this.putAll(kafkaProperties.properties)
    }
    return Admin.create(adminProperties)
  }

  @Bean(name = ["kafkaTopicName"])
  fun kafkaTopicName(): String = this.webserverConfigProperties.hostname

  @Bean
  fun kafkaReceiver(
    webserverConfigProperties: WebserverConfigProperties,
    userService: UserService,
    admin: Admin
  ): KafkaReceiver = KafkaReceiver(
    webserverConfigProperties = webserverConfigProperties,
    userService = userService,
    kafkaAdmin = admin,
    kafkaConfigProperties = this.customKafkaConfigProperties
  )
}