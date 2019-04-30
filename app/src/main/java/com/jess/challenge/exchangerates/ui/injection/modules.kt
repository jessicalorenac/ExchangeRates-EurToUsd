package com.jess.challenge.exchangerates.ui.injection

import com.jess.challenge.exchangerates.data.repository.ExchangeRatesRepositoryImpl
import com.jess.challenge.exchangerates.domain.interactor.GetRangedRateList
import com.jess.challenge.exchangerates.domain.interactor.GetRecentRate
import com.jess.challenge.exchangerates.domain.interactor.UseCase
import com.jess.challenge.exchangerates.domain.model.DateRange
import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity
import com.jess.challenge.exchangerates.domain.repository.ExchangeRatesRepository
import com.jess.challenge.exchangerates.ui.viewmodel.ExchangeRatesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module(override = true) {

    single<ExchangeRatesRepository> { ExchangeRatesRepositoryImpl(androidContext()) }

    //single<UseCase<EuroExchangeEntity, UseCase.None>> { GetRecentRate(get()) }

    //single<UseCase<List<EuroExchangeEntity>, DateRange>>{ GetRangedRateList(get()) }


}
//
//val domainModule = module("data", override = true) {
//
//
//}