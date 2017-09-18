package com.br.gbhaters.tracking

import com.br.gbhaters.http.LaPosteClient

fun main(args: Array<String>) {
    val client = LaPosteClient()

    val codeList = listOf("EY893054059FR", "EY893054116FR", "EY893054491FR")

    val trackingList = client.getBatchTrackingInfo(codeList)

    trackingList.forEach({
        println(it)
    })
}