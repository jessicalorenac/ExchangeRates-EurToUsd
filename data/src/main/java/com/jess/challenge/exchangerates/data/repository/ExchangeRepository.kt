package com.jess.challenge.exchangerates.data.repository

import com.jess.challenge.exchangerates.data.browse.EuroExchangeEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

interface ExchangeRepository {

    fun saveExchangeRates(exchangeEntities: List<EuroExchangeEntity>): Completable

    //TODO add parameter to getExchangeRates or modify useCases
    fun getExchangeRates(): Flowable<List<EuroExchangeEntity>>
    fun getExchangeRatesForPeriod(dateStart: Date, dateEnd: Date): Flowable<List<EuroExchangeEntity>>
}