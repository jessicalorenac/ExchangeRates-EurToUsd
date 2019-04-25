package com.jess.challenge.exchangerates.remote.mapper

import com.jess.challenge.exchangerates.data.browse.EuroExchangeEntity
import com.jess.challenge.exchangerates.remote.model.ExchangeRateModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EuroExchangeMapper : EntityMapper<ExchangeRateModel, List<EuroExchangeEntity>> {
    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd"
    }

    override fun mapFromRemote(model: ExchangeRateModel) = model.listRate.map {
            EuroExchangeEntity(LocalDate.parse(it.date, DateTimeFormatter.ofPattern(DATE_FORMAT)), it.value)
        }
}