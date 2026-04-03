package io.wesner.robert.cb1060.clamprotect.core

import io.wesner.robert.cb1060.clamprotect.core.report.Part
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockState
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
    material = typeId,
    data = data,
    world = world.name,
    location = location.serializableLocationInt(),
)
