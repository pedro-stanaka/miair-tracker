package com.br.gbhaters.persistence.entity

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.sound.midi.Track

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

            return Companion.parseFromJsonObject(obj)
        }

        companion object {
            fun parseFromJsonObject(obj: JsonObject): TrackingInfo {
                val formatter = DateTimeFormatter.ofPattern("d/MM/yyyy", Locale.FRANCE)

                val trackingCode = getString(obj, "code")
                val dateStr = getString(obj, "date")
                val date = if (!dateStr.isEmpty()) LocalDate.parse(dateStr, formatter) else null
                val status = getString(obj, "status")
                val message = getString(obj, "message")
                val link = getString(obj, "link")
                val type = getString(obj, "type")

                return TrackingInfo(trackingCode, date, status, message, link, type)
            }

            private fun getString(obj: JsonObject, prop: String) = if (obj.has(prop)) obj.get(prop).asString else ""
        }
    }

    class ListDeserializer: ResponseDeserializable<List<TrackingInfo>> {
        override fun deserialize(content: String): List<TrackingInfo> {
            val parser = JsonParser()
            val objectList = parser.parse(content).asJsonArray

            return objectList.map {
                if (it.asJsonObject.has("data"))
                    it.asJsonObject.get("data").asJsonObject
                else
                    it.asJsonObject.get("error").asJsonObject
            }.map {
                Deserializer.parseFromJsonObject(it)
            }
        }
    }
}