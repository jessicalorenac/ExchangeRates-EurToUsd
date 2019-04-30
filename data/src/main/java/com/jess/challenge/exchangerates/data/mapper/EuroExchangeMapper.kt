package com.jess.challenge.exchangerates.data.mapper

import com.jess.challenge.exchangerates.data.remote.model.ExchangeRateModel
import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EuroExchangeMapper : EntityMapper<ExchangeRateModel, List<EuroExchangeEntity>> {
    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd"
    }

    override fun mapModel(model: ExchangeRateModel) = model.listRate.map {
            EuroExchangeEntity(LocalDate.parse(it.date, DateTimeFormatter.ofPattern(DATE_FORMAT)), it.value)
        }
}