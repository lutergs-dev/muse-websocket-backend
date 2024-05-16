package dev.lutergs.muse.websocket.service

import dev.lutergs.muse.websocket.domain.entity.NowPlaying
import dev.lutergs.muse.websocket.domain.entity.User
import dev.lutergs.muse.websocket.domain.repository.UserAuthRepository
import dev.lutergs.muse.websocket.domain.repository.UserRepository
import org.springframework.web.socket.TextMessage

class UserService(
  private val userAuthRepository: UserAuthRepository,
  private val userRepository: UserRepository
) {

  fun getUserIdFromToken(token: String): Long? {
    return this.userAuthRepository.getUserId(token)
  }

  fun saveUser(user: User) {
    this.userRepository.saveUser(user)
  }

  fun sendFriendNowPlayingToUser(userId: Long, friendId: Long, friendNowPlaying: NowPlaying) {
    val textMessage = TextMessage(friendNowPlaying.toWebsocketMessage(friendId))
    this.userRepository.getUser(userId)
      ?.session
      ?.sendMessage(textMessage)
  }

  fun removeUser(userId: Long) {
    this.userRepository.removeUser(userId)
  }
}