package dev.lutergs.muse.websocket.infra.controller

import dev.lutergs.muse.websocket.domain.entity.NowPlaying
import dev.lutergs.muse.websocket.infra.config.properties.WebserverConfigProperties
import dev.lutergs.muse.websocket.infra.repository.kafka.proxy.KafkaRestProxyClient
import dev.lutergs.muse.websocket.service.UserService
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener

class KafkaReceiver(
  webserverConfigProperties: WebserverConfigProperties,
  private val userService: UserService,
  private val kafkaRestProxyClient: KafkaRestProxyClient
) {
  private val topicName = webserverConfigProperties.hostname
  private val logger = LoggerFactory.getLogger(KafkaReceiver::class.java)

  @PostConstruct
  fun buildTopic() {
    this.kafkaRestProxyClient.createTopic(NewTopic(this.topicName, 1, 3))
      .let { if (it) this.logger.debug("create success!") else this.logger.error("create topic failed!") }
  }

  @KafkaListener(topics = ["#{@kafkaTopicName}"])
  fun consume(records: ConsumerRecords<NowPlaying, dev.lutergs.muse.websocket.infra.repository.kafka.NotifierListDto>) {
    records.forEach {
      this.logger.debug("message received!\n\t\tkey : {}\n\t\tvalue : {}", it.key(), it.value())
      it.value().userIds.forEach { userId ->
        this.userService.sendFriendNowPlayingToUser(
          userId = userId,
          friendId = it.value().friendId,
          friendNowPlaying = it.key()
        )
      }
    }
  }

  @PreDestroy
  fun deleteTopic() {
    this.kafkaRestProxyClient.deleteTopic(this.topicName)
      .let { if (it) this.logger.debug("delete success!") else this.logger.warn("delete topic failed!") }
  }
}