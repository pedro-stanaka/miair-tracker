package com.br.gbhaters.persistence.connection

import com.br.gbhaters.config.DatabaseConfig
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.overriding
import org.jetbrains.exposed.sql.Database

object DbConnection {
    private val url: String
    private val username: String
    private val password: String

    init {
        val config = ConfigurationProperties.systemProperties() overriding
                EnvironmentVariables() overriding
                ConfigurationProperties.fromResource("application.properties")
        this.url = config[DatabaseConfig.url]
        this.username = config[DatabaseConfig.username]
        this.password = config[DatabaseConfig.password]
    }

    fun startExposedConnection() {
        Database.connect(url, "org.postgresql.Driver", this.username, this.password)
    }
}