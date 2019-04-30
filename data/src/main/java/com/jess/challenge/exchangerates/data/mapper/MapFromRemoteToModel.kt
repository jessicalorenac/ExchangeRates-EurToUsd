package com.jess.challenge.exchangerates.data.mapper

import com.jess.challenge.exchangerates.data.cache.utils.getLocalDate
import com.jess.challenge.exchangerates.data.remote.model.Rate
import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity

class MapFromRemoteToModel: EntityMapper<Rate, EuroExchangeEntity> {
    override fun mapModel(model: Rate): EuroExchangeEntity =
        EuroExchangeEntity(getLocalDate(model.date), model.value)
}