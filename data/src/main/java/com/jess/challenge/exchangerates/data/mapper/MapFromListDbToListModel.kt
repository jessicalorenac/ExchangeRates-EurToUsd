package com.jess.challenge.exchangerates.data.mapper

import com.jess.challenge.exchangerates.data.cache.entities.ExchangeRateDbEntity
import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity

class MapFromListDbToListModel : EntityMapper<List<ExchangeRateDbEntity>, List<EuroExchangeEntity>> {
    override fun mapModel(model: List<ExchangeRateDbEntity>) =
        model.map {
            EuroExchangeEntity(it.rateDate, it.rateVal)
        }
}