package com.br.gbhaters.config

import com.natpryce.konfig.PropertyGroup
import com.natpryce.konfig.getValue
import com.natpryce.konfig.stringType

object LaPosteApi : PropertyGroup() {
    val ProductionKey by stringType
}