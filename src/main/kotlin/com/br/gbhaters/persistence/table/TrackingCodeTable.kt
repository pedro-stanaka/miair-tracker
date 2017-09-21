package com.br.gbhaters.persistence.table

import org.jetbrains.exposed.dao.IntIdTable

object TrackingCodeTable: IntIdTable("tracking_codes") {
    val code = varchar("code", 50)
    val destination = varchar("destination", 50).nullable()
    val originalCode = varchar("original_code", 50).nullable()
}