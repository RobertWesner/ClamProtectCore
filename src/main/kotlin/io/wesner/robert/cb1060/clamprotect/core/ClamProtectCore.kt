package io.wesner.robert.cb1060.clamprotect.core

import io.wesner.robert.cb1060.clamprotect.core.exception.ClamProtectReloadDetectedException
import io.wesner.robert.cb1060.clamprotect.core.listener.ActivationListener
import io.wesner.robert.cb1060.clamprotect.core.listener.BlockListener
import io.wesner.robert.cb1060.clamprotect.core.listener.ChestListener
import io.wesner.robert.cb1060.clamprotect.core.listener.EntityListener
import io.wesner.robert.cb1060.clamprotect.core.listener.PlayerListener
import io.wesner.robert.cb1060.clamprotect.core.listener.ToolListener
import io.wesner.robert.cb1060.clamprotect.core.report.Reporter
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class ClamProtectCore : JavaPlugin() {
    internal val logger: Logger = Bukkit.getLogger()
    internal lateinit var reporter: Reporter
        private set
    lateinit var api: ClamProtectApi
        private set
    private var loaded
        get() = System.getProperty(LOADED_FLAG, "0") == "1"
        set(value) = System.setProperty(LOADED_FLAG, if (value) "1" else "0").let {}

    override fun onDisable() {
        reporter.shutdown()

        logger.info("Disabled ${description.name}.")
    }

    override fun onEnable() {
        if (loaded) {
            Thread {
                for (i in (1..5).reversed()) {
                    Bukkit.getLogger().severe("[$i] SERVER WAS RELOADED INSTEAD OF PROPERLY SHUT DOWN!")
                    Thread.sleep(1000)
                }

                Bukkit.shutdown()
            }.start()

            throw ClamProtectReloadDetectedException("Never reload ${description.name}, it requires a full restart!")
        }
        loaded = true

        plugin = this
        reporter = Reporter()
        api = ClamProtectApi()

        arrayOf(
            ActivationListener(),
            BlockListener(),
            ChestListener(),
            EntityListener(),
            PlayerListener(),
            ToolListener(),
        ).forEach { server.pluginManager.registerEvents(it, this) }

        logger.info("Enabled ${description.name} version ${description.version}.")
    }

    companion object {
        const val LOADED_FLAG = "ClamProtectCore_Plugin_loaded"
        @JvmStatic
        lateinit var plugin: ClamProtectCore
    }
}
