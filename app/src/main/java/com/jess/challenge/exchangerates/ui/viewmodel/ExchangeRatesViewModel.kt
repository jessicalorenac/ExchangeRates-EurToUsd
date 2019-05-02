package com.jess.challenge.exchangerates.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.Entry
import com.jess.challenge.exchangerates.domain.interactor.GetRangedFullEuroExchangeRates
import com.jess.challenge.exchangerates.domain.interactor.GetRecentRate
import com.jess.challenge.exchangerates.domain.interactor.UseCase
import com.jess.challenge.exchangerates.domain.model.DateRange
import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity
import com.jess.challenge.exchangerates.domain.model.FullEuroExchangeRate
import com.jess.challenge.exchangerates.ui.AppScope
import com.jess.challenge.exchangerates.ui.model.CurrentValue
import com.jess.challenge.exchangerates.ui.model.EuroChartModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ExchangeRatesViewModel(var getRecentRate: GetRecentRate, var getFullEuroRates: GetRangedFullEuroExchangeRates) :
    AbstractViewModel() {


    val chartDetail: MutableLiveData<EuroChartModel> = MutableLiveData()
    val currentValue: MutableLiveData<CurrentValue> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        AppScope.cancel()
    }

    fun loadCurrentValue() {
        AppScope.launch { getRecentRate(UseCase.None()) { it.either(::handleFailure, ::handleCurrentValue) } }
    }

    fun loadLiveData(dateRange: DateRange) {
        AppScope.launch {
            getFullEuroRates(dateRange) { it.either(::handleFailure, ::handleChartData) }
        }
    }

    private fun handleChartData(exchangeRate: FullEuroExchangeRate) {

        val listRates = exchangeRate.listRates.mapIndexed { index, euroExchangeEntity ->
            Entry(index.toFloat(), euroExchangeEntity.value)
        }

        this.chartDetail.value =
            EuroChartModel(
                listRates,
                exchangeRate.listRates,
                exchangeRate.listRates.first().value,
                exchangeRate.dateEnd,
                exchangeRate.maxRate,
                exchangeRate.minRate,
                exchangeRate.avgRate
            )
    }

    private fun handleCurrentValue(entity: EuroExchangeEntity) {
        this.currentValue.value = CurrentValue(entity.value)
    }

}