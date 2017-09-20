package com.br.gbhaters.config

import com.natpryce.konfig.getValue
import com.natpryce.konfig.stringType

object DatabaseConfig {
    val url by stringType
    val username by stringType
    val password by stringType
}