package io.wesner.robert.cb1060.clamprotect.core.config

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val version: Int,
    val amqp: Amqp,
) {
    @Serializable
    data class Amqp(
        val host: String,
        val port: Int,
        val username: String,
        val password: String,
    ) {
    }
}
