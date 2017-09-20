package com.br.gbhaters.util

import org.joda.time.DateTime
import java.time.LocalDate

object DateConversion {
    fun fromLocalDateToDateTime(ld: LocalDate?): DateTime? {
        if (ld == null) {
            return null
        }

        return DateTime(ld.year, ld.monthValue, ld.dayOfMonth, 0, 0)
    }
}