package io.wesner.robert.cb1060.clamprotect.core.report.message

import kotlinx.serialization.Serializable

@Serializable
sealed interface Report {
    /**
     * Trailing part of the routing key.
     *
     * "event.block.place", "event.block.break", etc.
     */
    val key: String
}
