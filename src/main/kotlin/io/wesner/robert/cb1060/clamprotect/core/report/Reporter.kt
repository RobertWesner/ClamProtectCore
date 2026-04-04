package io.wesner.robert.cb1060.clamprotect.core.report

import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.ConnectionFactory
import io.wesner.robert.cb1060.clamprotect.core.config.ConfigLoader
import io.wesner.robert.cb1060.clamprotect.core.exception.ClamProtectAmqpConnectionFailedException
import io.wesner.robert.cb1060.clamprotect.core.report.message.Report
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.util.Collections
import kotlin.jvm.optionals.getOrNull

class Reporter {
    private val json = Json {
        encodeDefaults = true
    }
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val inFlight = Collections.synchronizedSet(mutableSetOf<Job>())
    private val connection = ConnectionFactory().run {
        val amqp = ConfigLoader.config.amqp
        host = amqp.host
        port = amqp.port
        username = amqp.username
        password = amqp.password
        newConnection()
    }
    private val channel = run {
        val channel = connection.openChannel().getOrNull()
            ?: throw ClamProtectAmqpConnectionFailedException("Failed to connect to message broker. Is RabbitMQ down?")
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC, true)

        channel
    }

    @Volatile
    private var shuttingDown = false

    fun shutdown() = runBlocking {
        shuttingDown = true

        while (true) {
            val jobs = synchronized(inFlight) { inFlight.toList() }
            if (jobs.isEmpty()) break
            jobs.joinAll()
        }

        channel.close()
        connection.close()
    }

    fun publish(report: Report) {
        if (shuttingDown) return

        val job = scope.launch {
            channel.basicPublish(
                EXCHANGE,
                "$BASE_KEY.${report.key}",
                null,
                json.encodeToString(serializer(), report).toByteArray(),
            )
        }

        inFlight.add(job)
        job.invokeOnCompletion { inFlight.remove(job) }
    }

    companion object {
        private const val EXCHANGE = "clamprotect.report"
        const val BASE_KEY = "clamprotect.report"
    }
}
