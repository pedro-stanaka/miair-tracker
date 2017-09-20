package com.br.gbhaters.persistence.entity

import com.br.gbhaters.util.TrackingInfoBuilder
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.time.LocalDate

class TrackingInfo(
        var code: String?,
        var date: LocalDate?,
        var status: String,
        var message: String,
        var link: String,
        var type: String) {

    constructor() : this("", LocalDate.now(), "", "", "", "")

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
                val builder = TrackingInfoBuilder.builder()

                return builder.withCode(getString(obj, "code"))
                        .withDateFromString(getString(obj, "date"), "d/MM/yyyy")
                        .withStatus(getString(obj, "status"))
                        .withMessage(getString(obj, "message"))
                        .withLink(getString(obj, "link"))
                        .withType(getString(obj, "type"))
                        .build()
            }

            private fun getString(obj: JsonObject, prop: String) = if (obj.has(prop)) obj.get(prop).asString else ""
        }
    }

    class ListDeserializer : ResponseDeserializable<List<TrackingInfo>> {
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