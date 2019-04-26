package com.jess.challenge.exchangerates.domain.interactor

import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity
import com.jess.challenge.exchangerates.domain.repository.ExchangeRatesRepository

class GetCurrentValue(private val exchangeRepository: ExchangeRatesRepository): UseCase<EuroExchangeEntity, String>() {
    override suspend fun run(params: String) = exchangeRepository.currentRate(params)
}