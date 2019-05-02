package com.jess.challenge.exchangerates.data.mapper

import com.jess.challenge.exchangerates.data.cache.entities.ExchangeRateDbEntity
import com.jess.challenge.exchangerates.data.cache.utils.getLocalDate
import com.jess.challenge.exchangerates.data.remote.model.Rate

class MapFromRemoteToDB: EntityMapper<Rate, ExchangeRateDbEntity> {
    override fun mapModel(model: Rate) =
        ExchangeRateDbEntity(getLocalDate(model.date), model.value)
}