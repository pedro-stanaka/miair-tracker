package com.br.gbhaters.tracking

import com.br.gbhaters.http.LaPosteClient
import com.br.gbhaters.persistence.connection.DbConnection
import com.br.gbhaters.persistence.dao.TrackingInfoDao
import com.br.gbhaters.persistence.entity.TrackingInfo
import com.br.gbhaters.persistence.table.TrackingInfoTable
import com.br.gbhaters.util.DateConversion
import com.br.gbhaters.util.batch
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.io.File
import java.time.LocalDateTime

fun main(args: Array<String>) {
    val client = LaPosteClient()

    val codeList = File("todos_codigos.txt").readLines()

    DbConnection.startExposedConnection()

    val lastCode = TrackingInfoDao.getLastInsertedCode()

    val indexOf = if (codeList.indexOf(lastCode) != -1) codeList.indexOf(lastCode) else 0
    val newList = codeList.slice(indexOf until codeList.size)

    newList.asSequence().batch(120).forEach {
        println("Began request for LaPoste ${LocalDateTime.now()}")
        val trackingList = client.getBatchTrackingInfo(it)

        println("List size: ${trackingList.size} ${LocalDateTime.now()}")
        println("First code of batch: ${trackingList.first()}/${it.first()}")
        println("First code of batch: ${trackingList.last()}/${it.last()}")

        trackingList.groupBy { it.code }.forEach { (code, list) -> println("$code : count => ${list.size}") }


        transaction {
            val now = DateTime.now()
//            logger.addLogger(StdOutSqlLogger)
            it.zip(trackingList).forEach { (codeAttempt, trackingInfo) ->
                if (TrackingInfoDao.find { TrackingInfoTable.generatedCode eq codeAttempt }.empty()) {
                    createTracking(trackingInfo, codeAttempt, now)
                }
            }

        }

        println("######")
        Thread.sleep(4_000)
    }
}

private fun createTracking(trackingInfo: TrackingInfo, codeAttempt: String, now: DateTime) {
    val date = DateConversion.fromLocalDateToDateTime(trackingInfo.date)

    TrackingInfoDao.new {
        generatedCode = codeAttempt
        code = trackingInfo.code.orEmpty()
        updated = date
        status = trackingInfo.status
        message = trackingInfo.message
        link = trackingInfo.link
        type = trackingInfo.type
        created = now
    }
}