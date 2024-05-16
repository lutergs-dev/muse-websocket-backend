package dev.lutergs.muse.websocket.infra.repository.kafka.proxy

import dev.lutergs.muse.websocket.infra.config.properties.KafkaProxyConfigProperties
import dev.lutergs.muse.websocket.infra.repository.kafka.proxy.entity.CreateTopicRequest
import org.apache.kafka.clients.admin.NewTopic
import org.slf4j.LoggerFactory

class KafkaRestProxyClient(
  kafkaProxyConfigProperties: KafkaProxyConfigProperties
) {
  private val restClient = kafkaProxyConfigProperties.buildRestClient()
  private val logger = LoggerFactory.getLogger(KafkaRestProxyClient::class.java)

  fun createTopic(topic: NewTopic): Boolean {
    val request = CreateTopicRequest.fromNewTopic(topic)
    return this.restClient.post()
      .uri { it.path("/topics").build() }
      .body(request)
      .exchange { _, clientResponse ->
        if (clientResponse.statusCode.is2xxSuccessful) {
          true
        } else {
          this.logger.error("create topic failed!" +
              "\n\tstatus code: ${clientResponse.statusCode.value()}\n\t" +
              "message: ${clientResponse.body.readAllBytes().toString(Charsets.UTF_8)}"
          )
          false
        }
      }
  }

  fun deleteTopic(topicName: String): Boolean {
    return this.restClient.delete()
      .uri { it.path("/topics/${topicName}").build() }
      .exchange { _, clientResponse ->
        if (clientResponse.statusCode.is2xxSuccessful) {
          true
        } else {
          this.logger.error("delete topic failed!" +
              "\n\tstatus code: ${clientResponse.statusCode.value()}\n\t" +
              "message: ${clientResponse.body.readAllBytes().toString(Charsets.UTF_8)}"
          )
          false
        }
      }
  }
}