package com.br.gbhaters.tracking

import com.br.gbhaters.config.DatabaseConfig
import com.br.gbhaters.http.LaPosteClient
import com.br.gbhaters.persistence.dao.TrackingInfoDao
import com.br.gbhaters.persistence.table.TrackingInfoTable
import com.br.gbhaters.util.DateConversion
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.overriding
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
    val client = LaPosteClient()

    val config = ConfigurationProperties.systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromResource("application.properties")
    val url = config[DatabaseConfig.url]
    Database.connect(url, "org.postgresql.Driver", config[DatabaseConfig.username], config[DatabaseConfig.password])

    val codeList = listOf("EY893054059FR", "EY893054116FR", "EY893054491FR")

    val trackingList = client.getBatchTrackingInfo(codeList)

    trackingList.forEach { println(it) }

    transaction {
        logger.addLogger(StdOutSqlLogger)

        trackingList.forEach({
            if (TrackingInfoDao.find { TrackingInfoTable.code eq it.code }.empty()) {
                val date = DateConversion.fromLocalDateToDateTime(it.date)

                println(date)

                TrackingInfoDao.new {
                    code = it.code.orEmpty()
                    updated = date
                    status = it.status
                    message = it.message
                    link = it.link
                    type = it.type
                }
            }
        })
    }
}