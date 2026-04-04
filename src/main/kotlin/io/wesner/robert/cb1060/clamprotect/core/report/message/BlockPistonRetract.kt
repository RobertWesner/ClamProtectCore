package io.wesner.robert.cb1060.clamprotect.core.report.message

import io.wesner.robert.cb1060.clamprotect.core.report.Part
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class BlockPistonRetract(
    val time: Instant,
    val block: Part.Block,
    val direction: Part.BlockFace,
    val retractLocation: Part.LocationInt,
    val attachedBlockToRetract: Part.Block?,
    override val key: String = "event.block.piston.retract",
) : Report
