package io.wesner.robert.cb1060.clamprotect.core.listener

import io.wesner.robert.cb1060.clamprotect.core.ClamProtectCore
import io.wesner.robert.cb1060.clamprotect.core.report.message.BlockBreak
import io.wesner.robert.cb1060.clamprotect.core.report.message.BlockPlace
import io.wesner.robert.cb1060.clamprotect.core.serializableBlock
import io.wesner.robert.cb1060.clamprotect.core.serializableBlockState
import io.wesner.robert.cb1060.clamprotect.core.serializablePlayer
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import kotlin.time.Clock

class BlockListener : Listener {
    val api = ClamProtectCore.plugin.api

    @EventHandler(priority = Event.Priority.Monitor)
    fun onBlockBreak(event: BlockBreakEvent) {
        // TODO: player null?
        api.publish(
            BlockBreak(
                Clock.System.now(),
                event.player.serializablePlayer(),
                event.block.serializableBlock(),
            )
        )
    }

    @EventHandler(priority = Event.Priority.Monitor)
    fun onBlockPlace(event: BlockPlaceEvent) {
        // TODO: player null? probably never?
        api.publish(
            BlockPlace(
                Clock.System.now(),
                event.player.serializablePlayer(),
                event.block.serializableBlock(),
                event.blockReplacedState.serializableBlockState(),
            )
        )
    }

    // TODO: burn
    // TODO: fromto
    // TODO: leavesdecay
    // TODO: signchange
    // TODO: form
    // TODO: fade
}
