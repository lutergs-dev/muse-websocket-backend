package dev.lutergs.muse.websocket.infra

import dev.lutergs.muse.websocket.domain.repository.UserAuthRepository
import dev.lutergs.muse.websocket.infra.config.properties.MainServerConfigProperties
import org.springframework.web.client.RestClient

class UserAuthRepositoryImpl(
  mainServerConfigProperties: MainServerConfigProperties
): UserAuthRepository {

  private val client = RestClient.builder()
    .baseUrl(mainServerConfigProperties.serverUrl)
    .build()

  override fun getUserId(token: String): Long? {
    return this.client.get()
      .uri { it.path("/user/id").build() }
      .header("Authorization", "Bearer $token")
      .retrieve()
      .body(Long::class.java)
  }
}