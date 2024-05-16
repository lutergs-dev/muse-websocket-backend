package dev.lutergs.muse.websocket.infra

import dev.lutergs.muse.websocket.domain.entity.User
import dev.lutergs.muse.websocket.domain.repository.UserRepository
import dev.lutergs.muse.websocket.infra.repository.redis.ConnectedUserManager

class UserRepositoryImpl(
  private val connectedUserManager: ConnectedUserManager,
  private val userWebSocketSessionMap: UserWebSocketSessionMap
): UserRepository {

  override fun saveUser(user: User) {
    this.connectedUserManager.saveUser(user.id)
    this.userWebSocketSessionMap.saveUser(user)
  }

  override fun getUser(userId: Long): User? {
    return this.userWebSocketSessionMap.getUser(userId)
  }

  override fun removeUser(userId: Long) {
    this.connectedUserManager.removeUser(userId)
    this.userWebSocketSessionMap.removeUser(userId)
  }
}