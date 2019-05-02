package com.jess.challenge.exchangerates.data.mapper

import com.jess.challenge.exchangerates.data.cache.utils.getLocalDate
import com.jess.challenge.exchangerates.data.remote.model.ExchangeRateModel
import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity

class MapFromRemoteToListModel : EntityMapper<ExchangeRateModel, List<EuroExchangeEntity>> {
    override fun mapModel(model: ExchangeRateModel) =
        model.listRate.map {
            EuroExchangeEntity(getLocalDate(it.date), it.value)
        }.sortedBy { it.date }
}