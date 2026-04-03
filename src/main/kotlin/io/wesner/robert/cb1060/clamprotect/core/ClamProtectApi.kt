package io.wesner.robert.cb1060.clamprotect.core

import io.wesner.robert.cb1060.clamprotect.core.report.message.Report

open class ClamProtectApi {
    private val plugin = ClamProtectCore.plugin

    fun publish(report: Report) = plugin.reporter.publish(report)
}
