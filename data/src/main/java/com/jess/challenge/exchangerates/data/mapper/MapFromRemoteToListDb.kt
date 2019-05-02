package com.jess.challenge.exchangerates.data.mapper

import com.jess.challenge.exchangerates.data.cache.entities.ExchangeRateDbEntity
import com.jess.challenge.exchangerates.data.cache.utils.getLocalDate
import com.jess.challenge.exchangerates.data.remote.model.ExchangeRateModel

class MapFromRemoteToListDb : EntityMapper<ExchangeRateModel, List<ExchangeRateDbEntity>> {
    override fun mapModel(model: ExchangeRateModel) =
        model.listRate.map {
            ExchangeRateDbEntity(null, getLocalDate(it.date), it.value)
        }.sortedBy { it.rateDate }
}