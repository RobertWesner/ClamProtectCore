package io.wesner.robert.cb1060.clamprotect.core

import io.wesner.robert.cb1060.clamprotect.core.report.Part
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.BlockState
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

fun Location.serializableLocationFloat() = Part.LocationFloat(
    x = x,
    y = y,
    z = z,
)

fun Location.serializableLocationInt() = Part.LocationInt(
    x = blockX,
    y = blockY,
    z = blockZ,
)

fun Player.serializablePlayer() = Part.Player(
    name = name,
    displayName = displayName,
    uuid = uniqueId.toString(),
    address = address.toString(),
    location = location.serializableLocationFloat(),
)

fun BlockState.serializableBlockState() = Part.BlockState(
    material = typeId,
    data = rawData,
)

fun Block.serializableBlock() = Part.Block(
    state = Part.BlockState(
        material = typeId,
        data = data,
    ),
    world = world.name,
    location = location.serializableLocationInt(),
    pistonMoveReaction = pistonMoveReaction.id,
)

fun BlockFace.serializableBlockFace() = Part.BlockFace(
    modX = modX,
    modY = modY,
    modZ = modZ,
)

fun Entity.serializableEntity() = Part.Entity(
    entityClass = javaClass.name,
    entityId = entityId,
    uniqueId = uniqueId.toString(),
    world = world.name,
    location = location.serializableLocationFloat(),
)
