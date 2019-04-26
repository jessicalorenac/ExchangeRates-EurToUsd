package com.jess.challenge.exchangerates.domain.interactor

import com.jess.challenge.exchangerates.domain.model.DateRange
import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity
import com.jess.challenge.exchangerates.domain.repository.ExchangeRatesRepository

class GetRangeRates(private val exchangeRepository: ExchangeRatesRepository) :
    UseCase<List<EuroExchangeEntity>, DateRange>() {

    override suspend fun run(params: DateRange) = exchangeRepository.ratesRange(params.startDate, params.endDate)

}