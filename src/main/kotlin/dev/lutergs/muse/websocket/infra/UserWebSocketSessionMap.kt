package dev.lutergs.muse.websocket.infra

import dev.lutergs.muse.websocket.domain.entity.User
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

class UserWebSocketSessionMap {
  private val map = ConcurrentHashMap<Long, User>()
  private val logger = LoggerFactory.getLogger(UserWebSocketSessionMap::class.java)

  fun getUser(userId: Long): User? {
    return this.map[userId]
  }

  fun saveUser(user: User) {
    if (this.map.contains(user.id)) {
      this.logger.warn("유저 ${user.id} 가 연결되어 있는 상태에서 saveUser 를 호출했습니다. 연결이 강제 업데이트됩니다.")
      user.session.close()
    }
    this.map[user.id] = user
  }

  fun removeUser(userId: Long) {
    this.map[userId]
      ?.let { user ->
        user.session.close()
        this.map.remove(userId)
      }
  }
}