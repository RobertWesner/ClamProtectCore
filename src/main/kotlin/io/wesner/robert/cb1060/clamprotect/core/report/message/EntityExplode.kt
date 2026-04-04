package io.wesner.robert.cb1060.clamprotect.core.report.message

import io.wesner.robert.cb1060.clamprotect.core.report.Part
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class EntityExplode(
    val time: Instant,
    val location: Part.LocationFloat,
    val entity: Part.Entity,
    val yield: Float,
    val blocks: List<Part.Block>,
    val target: Part.Player?,
    override val key: String = "event.entity.explode"
) : Report
