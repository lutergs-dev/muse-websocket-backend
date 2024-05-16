package dev.lutergs.muse.websocket.infra.repository.redis

import dev.lutergs.muse.websocket.infra.config.properties.WebserverConfigProperties
import jakarta.annotation.PreDestroy
import org.springframework.data.redis.core.StringRedisTemplate


class ConnectedUserManager(
  webserverConfigProperties: WebserverConfigProperties,
  private val stringRedisTemplate: StringRedisTemplate
) {
  private val hostName = webserverConfigProperties.hostname

  fun saveUser(userId: Long) {
    this.stringRedisTemplate.opsForValue().set(userId.toString(), this.hostName)
    this.stringRedisTemplate.opsForSet().add(this.hostName, userId.toString())
  }

  fun removeUser(userId: Long) {
    this.stringRedisTemplate.delete(userId.toString())
    this.stringRedisTemplate.opsForSet().remove(this.hostName, userId.toString())
  }

  @PreDestroy
  private fun onClose() {
    this.stringRedisTemplate.opsForSet().members(this.hostName)
      ?.forEach { this.stringRedisTemplate.delete(it) }
    this.stringRedisTemplate.delete(this.hostName)
  }
}