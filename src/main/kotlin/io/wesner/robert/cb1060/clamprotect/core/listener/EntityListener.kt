package io.wesner.robert.cb1060.clamprotect.core.listener

import io.wesner.robert.cb1060.clamprotect.core.ClamProtectCore
import io.wesner.robert.cb1060.clamprotect.core.report.message.EntityExplode
import io.wesner.robert.cb1060.clamprotect.core.serializableBlock
import io.wesner.robert.cb1060.clamprotect.core.serializableEntity
import io.wesner.robert.cb1060.clamprotect.core.serializableLocationFloat
import io.wesner.robert.cb1060.clamprotect.core.serializablePlayer
import org.bukkit.entity.Creeper
import org.bukkit.entity.Entity
import org.bukkit.entity.Fireball
import org.bukkit.entity.Ghast
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.entity.EntityTargetEvent
import kotlin.collections.remove
import kotlin.time.Clock

// FYI: CreatureSpawnEvent is not fired on fireball shots :)

class EntityListener : Listener {
    val api = ClamProtectCore.plugin.api

    val creeperTargetedPlayers = mutableMapOf<Creeper, Player>()
    val ghastTargetedPlayers = mutableMapOf<Ghast, Player>()

    // TODO: there seems to be no clean way to track tnt ignition

    @EventHandler(priority = Event.Priority.Monitor)
    fun onEntityTarget(event: EntityTargetEvent) {
        if (event.target !is Player) return

        when (event.entity) {
            is Creeper -> creeperTargetedPlayers[event.entity as Creeper] = event.target as Player
            is Ghast -> ghastTargetedPlayers[event.entity as Ghast] = event.target as Player
        }
    }

    @EventHandler(priority = Event.Priority.Monitor)
    fun onEntityDeath(event: EntityDeathEvent) {
        removeTargeting(event.entity)
    }

    @EventHandler(priority = Event.Priority.Monitor)
    fun onEntityExplode(event: EntityExplodeEvent) {
        api.publish(
            EntityExplode(
                Clock.System.now(),
                event.location.serializableLocationFloat(),
                event.entity.serializableEntity(),
                event.yield,
                event.blockList().map { it.serializableBlock() },
                getTarget(event.entity)?.serializablePlayer(),
            )
        )

        removeTargeting(event.entity)
    }

    private fun removeTargeting(entity: Entity) {
        when (entity) {
            is Creeper -> creeperTargetedPlayers
            is Ghast -> ghastTargetedPlayers
            else -> return
        }.remove(entity)
    }

    private fun getTarget(entity: Entity) = when (entity) {
        is Creeper -> creeperTargetedPlayers[entity]
        is Ghast -> ghastTargetedPlayers[entity]
        is Fireball -> ghastTargetedPlayers[entity.shooter]
        else -> null
    }
}
