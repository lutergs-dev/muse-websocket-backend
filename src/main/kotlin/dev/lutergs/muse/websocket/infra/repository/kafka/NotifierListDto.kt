package dev.lutergs.muse.websocket.infra.repository.kafka

data class NotifierListDto (
  val userIds: List<Long>,
  val friendId: Long
)