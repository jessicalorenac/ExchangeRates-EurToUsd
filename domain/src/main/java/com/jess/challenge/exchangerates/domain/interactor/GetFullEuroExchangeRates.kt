package com.jess.challenge.exchangerates.domain.interactor

import com.jess.challenge.exchangerates.domain.model.DateRange
import com.jess.challenge.exchangerates.domain.model.FullEuroExchangeRate
import com.jess.challenge.exchangerates.domain.repository.ExchangeRatesRepository

class GetFullEuroExchangeRates(private val exchangeRepository: ExchangeRatesRepository) :
    UseCase<FullEuroExchangeRate, DateRange>() {

    override suspend fun run(params: DateRange) = exchangeRepository.fullRatesRange(params.startDate, params.endDate)

}