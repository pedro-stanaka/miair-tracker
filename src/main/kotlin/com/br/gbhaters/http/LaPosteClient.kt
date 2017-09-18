package com.br.gbhaters.http

import com.br.gbhaters.config.LaPosteApi
import com.br.gbhaters.persistence.entity.TrackingInfo
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.overriding

class LaPosteClient {
    private val API_BASE = "https://api.laposte.fr/suivi/v1"
    private var API_KEY: String

    init {
        FuelManager.instance.basePath = API_BASE

        val config = ConfigurationProperties.systemProperties() overriding
                EnvironmentVariables() overriding
                ConfigurationProperties.fromResource("application.properties")
        API_KEY = config[LaPosteApi.ProductionKey]
    }

    fun getTrackingInfo(trackingCode: String): TrackingInfo? {
        val (_, _, result) = Fuel.get("/$trackingCode").header(mapOf(
                Pair("X-Okapi-Key", API_KEY)
        )).responseObject(TrackingInfo.Deserializer())

        return result.get()
    }

    fun getBatchTrackingInfo(codeList: List<String>): List<TrackingInfo> {
        val codesString = codeList.joinToString(separator = ",")

        val (_, _, result) = Fuel.get("/list?codes=$codesString").header(mapOf(
                Pair("X-Okapi-Key", API_KEY)
        )).responseObject(TrackingInfo.ListDeserializer())

        return result.get()
    }
}