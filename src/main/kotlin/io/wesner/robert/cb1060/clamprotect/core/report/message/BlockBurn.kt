package io.wesner.robert.cb1060.clamprotect.core.report.message

import io.wesner.robert.cb1060.clamprotect.core.report.Part
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class BlockBurn(
    val time: Instant,
    val block: Part.Block,
    override val key: String = "event.block.burn",
) : Report
