package com.br.gbhaters.util

import com.br.gbhaters.persistence.entity.TrackingInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TrackingInfoBuilder {
    companion object {
        fun builder(): TrackingInfoBuilder {
            return TrackingInfoBuilder()
        }
    }

    private val infoInstance = TrackingInfo()



    fun withCode(code: String): TrackingInfoBuilder {
        this.infoInstance.code = code

        return this
    }

    fun withDate(date: LocalDate): TrackingInfoBuilder {
        this.infoInstance.date = date

        return this
    }

    fun withDateFromString(dateString: String, format: String): TrackingInfoBuilder {
        val formatter = DateTimeFormatter.ofPattern(format, Locale.FRANCE)
        this.infoInstance.date = if (!dateString.isEmpty()) LocalDate.parse(dateString, formatter) else null

        return this
    }

    fun withStatus(status: String): TrackingInfoBuilder {
        this.infoInstance.status = status

        return this
    }

    fun withMessage(message: String): TrackingInfoBuilder {
        this.infoInstance.message = message

        return this
    }

    fun withLink(link: String): TrackingInfoBuilder {
        this.infoInstance.link = link

        return this
    }

    fun withType(type: String): TrackingInfoBuilder {
        this.infoInstance.type = type

        return this
    }

    fun build(): TrackingInfo {
        return this.infoInstance
    }
}