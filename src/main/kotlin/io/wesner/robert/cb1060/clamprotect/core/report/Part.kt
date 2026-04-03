package io.wesner.robert.cb1060.clamprotect.core.report

import kotlinx.serialization.Serializable
import java.util.UUID

class Part {
    @Serializable
    data class LocationFloat(
        val x: Double,
        val y: Double,
        val z: Double,
    )

    @Serializable
    data class LocationInt(
        val x: Int,
        val y: Int,
        val z: Int,
    )

    @Serializable
    data class Player(
        val name: String,
        val displayName: String,
        val uuid: String,
        val address: String,
        val location: LocationFloat,
    )

    @Serializable
    data class BlockState(
        val material: Int,
        val data: Byte,
    )

    @Serializable
    data class Block(
        val material: Int,
        val data: Byte,
        val world: String,
        val location: LocationInt,
    )
}
