package com.br.gbhaters.tracking

import com.br.gbhaters.http.LaPosteClient

fun main(args: Array<String>) {
    val code = "EY893053305FR"

    val client = LaPosteClient()

    val trackingInfo = client.getTrackingInfo(code)

    if (trackingInfo != null) {
        println(trackingInfo)
    }
}