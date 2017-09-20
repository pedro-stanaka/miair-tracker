package com.br.gbhaters.persistence.dao

import com.br.gbhaters.persistence.table.TrackingInfoTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass


class TrackingInfoDao (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TrackingInfoDao>(TrackingInfoTable)

    var code by TrackingInfoTable.code
    var updated by TrackingInfoTable.updated
    var status by TrackingInfoTable.status
    var message by TrackingInfoTable.message
    var link by TrackingInfoTable.link
    var type by TrackingInfoTable.type
}