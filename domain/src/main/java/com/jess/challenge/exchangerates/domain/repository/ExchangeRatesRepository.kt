package com.jess.challenge.exchangerates.domain.repository

import com.jess.challenge.exchangerates.domain.Either
import com.jess.challenge.exchangerates.domain.exception.Failure
import com.jess.challenge.exchangerates.domain.model.DateRange
import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity
import com.jess.challenge.exchangerates.domain.model.FullEuroExchangeRate
import java.time.LocalDate

interface ExchangeRatesRepository {
    fun getMostRecentRate(): Either<Failure, EuroExchangeEntity>
    fun getRangedRateList(dateRange: DateRange): Either<Failure, List<EuroExchangeEntity>>
    fun getRangedFullRate(dateRange: DateRange): Either<Failure, FullEuroExchangeRate>
}