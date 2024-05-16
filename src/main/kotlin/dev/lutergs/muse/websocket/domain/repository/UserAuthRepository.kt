package dev.lutergs.muse.websocket.domain.repository

interface UserAuthRepository {
  fun getUserId(token: String): Long?
}


