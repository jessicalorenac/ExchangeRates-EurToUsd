package com.jess.challenge.exchangerates.ui.browse

//import com.jess.challenge.exchangerates.data.browse.EuroExchangeEntity
//import com.jess.challenge.exchangerates.ui.model.ExchangeResourceState
//
//sealed class ExchangeBrowseState(
//    val exchangeResourceState: ExchangeResourceState,
//    val data: List<EuroExchangeEntity>? = null,
//    val errorMessage: String? = null) {
//
//    data class Success(private val rates: List<EuroExchangeEntity>) :
//        ExchangeBrowseState(ExchangeResourceState.SUCCESS, rates)
//
//    data class Error(private val message: String? = null) :
//        ExchangeBrowseState(ExchangeResourceState.ERROR, errorMessage = message)
//
//    object Loading: ExchangeBrowseState(ExchangeResourceState.LOADING)
//
//}