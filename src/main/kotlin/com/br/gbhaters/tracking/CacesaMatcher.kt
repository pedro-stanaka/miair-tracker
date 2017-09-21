package com.br.gbhaters.tracking

import com.br.gbhaters.persistence.connection.DbConnection
import com.br.gbhaters.persistence.dao.TrackingCodeDao
import com.br.gbhaters.persistence.table.TrackingCodeTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.jsoup.Jsoup


private val NO_MATCH = "NO_MATCH"

fun main(args: Array<String>) {

    DbConnection.startExposedConnection()

    transaction {
        TrackingCodeDao.find { TrackingCodeTable.originalCode.isNull() }.limit(100).forUpdate().forEach { code ->
            val url = "http://www.cacesa.com/Consulta/Envios?q=${code.code.trim()}"

            println(url)

            val response = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.91 Safari/537.36")
                    .timeout(10_000)
                    .execute()

            if (response.statusCode() in 200 until 400) {

                val spanKfy = response
                        .parse()
                        .select("#contentLeft_ucConsultaEnvios_gvConsultaEnvios_lblRefRemitente_0")

                if (spanKfy.size > 0) {
                    transaction {
                        TrackingCodeTable.update({
                            TrackingCodeTable.code eq code.code
                        }) {
                            it[TrackingCodeTable.originalCode] = spanKfy.text()
                        }
                    }
                } else {
                    transaction {
                        TrackingCodeTable.update({
                            TrackingCodeTable.code eq code.code
                        }) {
                            it[TrackingCodeTable.originalCode] = NO_MATCH
                        }
                    }
                }
            }

            Thread.sleep(1_000)
        }
    }
}