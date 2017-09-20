package com.br.gbhaters.persistence.dao

import com.br.gbhaters.persistence.connection.DbConnection
import com.br.gbhaters.persistence.table.TrackingInfoTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction


class TrackingInfoDao (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TrackingInfoDao>(TrackingInfoTable)

    var generatedCode by TrackingInfoTable.generatedCode
    var code by TrackingInfoTable.code
    var updated by TrackingInfoTable.updated
    var status by TrackingInfoTable.status
    var message by TrackingInfoTable.message
    var link by TrackingInfoTable.link
    var type by TrackingInfoTable.type
    var created by TrackingInfoTable.created

    fun getLastInsertedCode(): String {
        DbConnection.startExposedConnection()

        transaction {
            TrackingInfoDao.searchQuery(Op.build { TrackingInfoTable.code == "" }).orderBy()
        }
    }
}