package io.wesner.robert.cb1060.clamprotect.core.report.message

import io.wesner.robert.cb1060.clamprotect.core.report.Part
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class BlockFade(
    val time: Instant,
    val block: Part.Block,
    val newState: Part.BlockState,
    override val key: String = "event.block.form"
) : Report
