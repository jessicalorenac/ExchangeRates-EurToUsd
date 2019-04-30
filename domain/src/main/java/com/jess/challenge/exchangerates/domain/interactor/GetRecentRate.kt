package com.jess.challenge.exchangerates.domain.interactor

import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity
import com.jess.challenge.exchangerates.domain.repository.ExchangeRatesRepository

class GetRecentRate(private val exchangeRepository: ExchangeRatesRepository): UseCase<EuroExchangeEntity, UseCase.None>() {
    override suspend fun run(params: None) = exchangeRepository.getMostRecentRate()
}