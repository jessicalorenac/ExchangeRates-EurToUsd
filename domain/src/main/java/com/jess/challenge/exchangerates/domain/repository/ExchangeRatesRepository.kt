package com.jess.challenge.exchangerates.domain.repository

import com.jess.challenge.exchangerates.domain.Either
import com.jess.challenge.exchangerates.domain.exception.Failure
import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity
import com.jess.challenge.exchangerates.domain.model.FullEuroExchangeRate

interface ExchangeRatesRepository {
    fun currentRate(today: String): Either<Failure, EuroExchangeEntity>
    fun ratesRange(dateStart: String, dateEnd: String): Either<Failure, List<EuroExchangeEntity>>
    fun fullRatesRange(dateStart: String, dateEnd: String): Either<Failure, FullEuroExchangeRate>
}