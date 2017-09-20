package com.br.gbhaters.persistence.table

import org.jetbrains.exposed.dao.IntIdTable

object TrackingInfoTable : IntIdTable("tracking") {
    val generatedCode = varchar("generated_code", 50)
    val code = varchar("code", 50)
    val updated = date("updated").nullable()
    val status = varchar("status", 200).nullable()
    val message = text("message")
    val link = varchar("link", 280)
    val type = varchar("sub_carrier", 100)
    val created = datetime("created")
}