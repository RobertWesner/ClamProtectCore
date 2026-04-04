package io.wesner.robert.cb1060.clamprotect.core.report.message

import io.wesner.robert.cb1060.clamprotect.core.report.Part
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class BlockFromTo(
    val time: Instant,
    val block: Part.Block,
    val blockTo: Part.Block,
    val face: Part.BlockFace,
    override val key: String = "event.block.from-to"
) : Report
