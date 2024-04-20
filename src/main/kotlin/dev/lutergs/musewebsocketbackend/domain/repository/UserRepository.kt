package dev.lutergs.musewebsocketbackend.domain.repository

interface UserRepository {
  fun getUserId(token: String): Long
}