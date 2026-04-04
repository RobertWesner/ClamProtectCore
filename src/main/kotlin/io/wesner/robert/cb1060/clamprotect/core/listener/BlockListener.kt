package io.wesner.robert.cb1060.clamprotect.core.listener

import io.wesner.robert.cb1060.clamprotect.core.ClamProtectCore
import io.wesner.robert.cb1060.clamprotect.core.report.message.BlockBreak
import io.wesner.robert.cb1060.clamprotect.core.report.message.BlockBurn
import io.wesner.robert.cb1060.clamprotect.core.report.message.BlockFade
import io.wesner.robert.cb1060.clamprotect.core.report.message.BlockForm
import io.wesner.robert.cb1060.clamprotect.core.report.message.BlockFromTo
import io.wesner.robert.cb1060.clamprotect.core.report.message.BlockPlace
import io.wesner.robert.cb1060.clamprotect.core.report.message.LeavesDecay
import io.wesner.robert.cb1060.clamprotect.core.report.message.SignChange
import io.wesner.robert.cb1060.clamprotect.core.serializableBlock
import io.wesner.robert.cb1060.clamprotect.core.serializableBlockFace
import io.wesner.robert.cb1060.clamprotect.core.serializableBlockState
import io.wesner.robert.cb1060.clamprotect.core.serializablePlayer
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockBurnEvent
import org.bukkit.event.block.BlockFadeEvent
import org.bukkit.event.block.BlockFormEvent
import org.bukkit.event.block.BlockFromToEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.block.LeavesDecayEvent
import org.bukkit.event.block.SignChangeEvent
import kotlin.time.Clock

class BlockListener : Listener {
    val api = ClamProtectCore.plugin.api

    @EventHandler(priority = Event.Priority.Monitor)
    fun onBlockBreak(event: BlockBreakEvent) = api.publish(
        BlockBreak(
            Clock.System.now(),
            event.player.serializablePlayer(),
            event.block.serializableBlock(),
        )
    )

    @EventHandler(priority = Event.Priority.Monitor)
    fun onBlockPlace(event: BlockPlaceEvent) = api.publish(
        BlockPlace(
            Clock.System.now(),
            event.player.serializablePlayer(),
            event.block.serializableBlock(),
            event.blockReplacedState.serializableBlockState(),
        )
    )

    @EventHandler(priority = Event.Priority.Monitor)
    fun onBlockBurn(event: BlockBurnEvent) = api.publish(
        BlockBurn(
            Clock.System.now(),
            event.block.serializableBlock(),
        )
    )

    @EventHandler(priority = Event.Priority.Monitor)
    fun onBlockFromTo(event: BlockFromToEvent) = api.publish(
        BlockFromTo(
            Clock.System.now(),
            event.block.serializableBlock(),
            event.toBlock.serializableBlock(),
            event.face.serializableBlockFace(),
        )
    )

    @EventHandler(priority = Event.Priority.Monitor)
    fun onBlockForm(event: BlockFormEvent) = api.publish(
        BlockForm(
            Clock.System.now(),
            event.block.serializableBlock(),
            event.newState.serializableBlockState(),
        )
    )
    @EventHandler(priority = Event.Priority.Monitor)
    fun onBlockFade(event: BlockFadeEvent) = api.publish(
        BlockFade(
            Clock.System.now(),
            event.block.serializableBlock(),
            event.newState.serializableBlockState(),
        )
    )
    @EventHandler(priority = Event.Priority.Monitor)
    fun onLeavesDecay(event: LeavesDecayEvent) = api.publish(
        LeavesDecay(
            Clock.System.now(),
            event.block.serializableBlock(),
        )
    )

    @EventHandler(priority = Event.Priority.Monitor)
    fun onSignChange(event: SignChangeEvent) = api.publish(
        SignChange(
            Clock.System.now(),
            event.player.serializablePlayer(),
            event.block.serializableBlock(),
            event.lines.toList(),
        )
    )
}
