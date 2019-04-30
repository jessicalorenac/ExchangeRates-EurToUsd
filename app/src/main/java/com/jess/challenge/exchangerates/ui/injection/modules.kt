//package com.jess.challenge.exchangerates.ui.injection
////
////import com.jess.challenge.exchangerates.data.browse.interactor.GetExchangeRates
////import com.jess.challenge.exchangerates.data.executor.JobExecutor
////import com.jess.challenge.exchangerates.data.executor.PostExecutionThread
////import com.jess.challenge.exchangerates.data.executor.ThreadExecutor
////import com.jess.challenge.exchangerates.remote.ExchangeServiceFactory
////import com.jess.challenge.exchangerates.ui.UiThread
////import com.jess.challenge.exchangerates.ui.browse.GetExchangeRatesViewModel
////import org.koin.android.viewmodel.ext.koin.viewModel
////import org.koin.dsl.module.module
////
////val applicationModule = module(override = true) {
////
////    single { JobExecutor() as ThreadExecutor }
////    single { UiThread() as PostExecutionThread }
////
////    factory { ExchangeServiceFactory.makeExchangeService()}
////}
////
////val browseModule = module("Browse", override = true) {
////    //TODO add ViewAdapter??
////    factory { GetExchangeRates(get(), get(), get()) }
////    viewModel { GetExchangeRatesViewModel(get()) }
////}