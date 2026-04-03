package io.wesner.robert.cb1060.clamprotect.core.config

import com.akuleshov7.ktoml.Toml
import com.akuleshov7.ktoml.TomlInputConfig
import io.wesner.robert.cb1060.clamprotect.core.ClamProtectCore
import io.wesner.robert.cb1060.clamprotect.core.exception.ClamProtectStaleConfigException
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import sun.misc.IOUtils
import java.io.File

object ConfigLoader {
    private const val CONFIG_FILENAME = "/config.toml"

    private val plugin = ClamProtectCore.plugin
    private val toml = Toml(
        inputConfig = TomlInputConfig(
            ignoreUnknownNames = true,
        )
    )
    val config: Config

    init {
        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdir()
        }

        val configFile = File(plugin.dataFolder, CONFIG_FILENAME)
        if (!configFile.exists()) {
            configFile.outputStream().use { stream ->
                defaultConfigStream().copyTo(stream)
            }
        }

        val configFileText = configFile.readText()
        val currentVersion = toml.decodeFromString<Version>(serializer(), configFileText)
        val latestVersion = toml.decodeFromString<Version>(serializer(), String(IOUtils.readAllBytes(defaultConfigStream())))
        if (currentVersion != latestVersion) {
            throw ClamProtectStaleConfigException("ClamProtect configuration version $currentVersion does not match expected version $latestVersion. Please migrate.")
        }

        config = toml.decodeFromString(serializer(), configFileText)
    }

    private fun defaultConfigStream() = this.javaClass.getResourceAsStream(CONFIG_FILENAME)!!

    @Serializable
    private data class Version(val version: Int)
}
