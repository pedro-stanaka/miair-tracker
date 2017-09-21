package com.br.gbhaters.persistence.dao

import com.br.gbhaters.persistence.table.TrackingCodeTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class TrackingCodeDao (id: EntityID<Int>): IntEntity(id)  {
    companion object : IntEntityClass<TrackingCodeDao>(TrackingCodeTable)

    val code by TrackingCodeTable.code
    val destination by TrackingCodeTable.destination
    var originalCode by TrackingCodeTable.originalCode
}