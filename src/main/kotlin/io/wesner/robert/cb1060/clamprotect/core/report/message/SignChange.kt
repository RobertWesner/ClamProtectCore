package io.wesner.robert.cb1060.clamprotect.core.report.message

import io.wesner.robert.cb1060.clamprotect.core.report.Part
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class SignChange(
    val time: Instant,
    val player: Part.Player,
    val block: Part.Block,
    val lines: List<String>,
    override val key: String = "event.block.sign-change",
) : Report
