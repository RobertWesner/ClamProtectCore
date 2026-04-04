package io.wesner.robert.cb1060.clamprotect.core.report.message

import io.wesner.robert.cb1060.clamprotect.core.report.Part
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class BlockPlace(
    val time: Instant,
    val player: Part.Player,
    val block: Part.Block,
    val previous: Part.BlockState,
    override val key: String = "event.block.place",
) : Report
