package dev.lutergs.muse.websocket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MuseWebsocketBackendApplication

fun main(args: Array<String>) {
  runApplication<MuseWebsocketBackendApplication>(*args)
}
