package dev.lutergs.muse.websocket.domain.repository

import dev.lutergs.muse.websocket.domain.entity.User

interface UserRepository {
  fun saveUser(user: User)
  fun getUser(userId: Long): User?
  fun removeUser(userId: Long)
}