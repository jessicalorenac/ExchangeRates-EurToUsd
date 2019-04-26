package com.jess.challenge.exchangerates.domain.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EuroExchangeEntity(val date: LocalDate, val value: Float) {
    companion object {
        const val DEFAULT_DATE = "2000-01-01"
        const val DATE_FORMAT = "yyyy-MM-dd"

        fun empty() = EuroExchangeEntity(LocalDate.parse(DEFAULT_DATE, DateTimeFormatter.ofPattern(DATE_FORMAT)), 0.toFloat())
    }
}