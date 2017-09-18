package com.br.gbhaters.persistence.entity

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.JsonParser
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TrackingInfo(
        val code: String?,
        val date: LocalDate?,
        val status: String,
        val message: String,
        val link: String,
        val type: String) {

    override fun toString(): String {
        return "TrackingInfo(trackingCode='$code', date=$date, status='$status', message='$message', link='$link', type='$type')"
    }

    class Deserializer : ResponseDeserializable<TrackingInfo> {
        override fun deserialize(content: String): TrackingInfo {
            val parser = JsonParser()
            val obj = parser.parse(content).asJsonObject

            val formatter = DateTimeFormatter.ofPattern("d/MM/yyyy", Locale.FRANCE)

            val trackingCode = obj.get("code").asString
            val dateStr = obj.get("date").asString
            val date = LocalDate.parse(dateStr, formatter)
            val status = obj.get("status").asString
            val message = obj.get("message").asString
            val link = obj.get("link").asString
            val type = obj.get("type").asString

            return TrackingInfo(trackingCode, date, status, message, link, type)
        }
    }
}