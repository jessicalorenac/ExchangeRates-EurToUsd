package com.jess.challenge.exchangerates.domain.interactor

import com.jess.challenge.exchangerates.domain.model.DateRange
import com.jess.challenge.exchangerates.domain.model.FullEuroExchangeRate
import com.jess.challenge.exchangerates.domain.repository.ExchangeRatesRepository

class GetRangedFullEuroExchangeRates(private val exchangeRepository: ExchangeRatesRepository) :
    UseCase<FullEuroExchangeRate, DateRange>() {

    override suspend fun run(params: DateRange) = exchangeRepository.getRangedFullRate(params)

}