package io.wesner.robert.cb1060.clamprotect.core.report.message

import io.wesner.robert.cb1060.clamprotect.core.report.Part
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class BlockPistonExtend(
    val time: Instant,
    val block: Part.Block,
    val direction: Part.BlockFace,
    val blocks: List<Part.Block>,
    val length: Int,
    override val key: String = "event.block.piston.extend",
) : Report
