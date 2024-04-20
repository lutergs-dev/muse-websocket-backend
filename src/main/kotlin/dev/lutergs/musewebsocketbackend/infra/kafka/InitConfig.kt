package dev.lutergs.musewebsocketbackend.infra.kafka

import jakarta.annotation.PostConstruct
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic

class InitConfig(
  private val adminClient: AdminClient
) {

  @PostConstruct
  fun buildTopic() {
    this.adminClient.createTopics(listOf(NewTopic()))
  }
}