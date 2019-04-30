package com.jess.challenge.exchangerates.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jess.challenge.exchangerates.domain.Either
import com.jess.challenge.exchangerates.domain.interactor.GetRangedRateList
import com.jess.challenge.exchangerates.domain.interactor.GetRecentRate
import com.jess.challenge.exchangerates.domain.interactor.UseCase
import com.jess.challenge.exchangerates.domain.model.DateRange
import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity
import com.jess.challenge.exchangerates.ui.model.EuroChartModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExchangeRatesViewModel(

) : AbstractViewModel() {

    var getRecentRate: GetRecentRate? = null
    var getRangedRateList: GetRangedRateList? = null

    val chartDetail: MutableLiveData<EuroChartModel> = MutableLiveData()

    fun loadViewData(dateRange: DateRange) = CoroutineScope(Dispatchers.Main).launch {
        val currentRateResult = getRecentRate?.let { it(UseCase.None()) }?.await()
        val listRates = getRangedRateList?.let { it(dateRange) }?.await()

        when {
            currentRateResult is Either.Right && listRates is Either.Right -> handleChartData(currentRateResult.b, listRates.b)
            currentRateResult is Either.Left -> handleFailure(currentRateResult.a)
            listRates is Either.Left -> handleFailure(listRates.a)
        }
    }

    private fun handleChartData(rate: EuroExchangeEntity, listRates: List<EuroExchangeEntity>) {
        this.chartDetail.value =
            EuroChartModel(listRates, rate.value, rate.value, rate.date.toString(), 0.0F, 0.0F, 0.0F)
    }

}