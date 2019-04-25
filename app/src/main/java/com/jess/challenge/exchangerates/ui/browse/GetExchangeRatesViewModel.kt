package com.jess.challenge.exchangerates.ui.browse

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import com.jess.challenge.exchangerates.data.browse.interactor.GetExchangeRates

class GetExchangeRatesViewModel(val flowableExchangeRates: GetExchangeRates): ViewModel() {

    private val ratesLiveData: MutableLiveData<ExchangeBrowseState> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun getExchangeRates() = ratesLiveData

    fun fetchExchangeRates() {
        ratesLiveData.postValue(ExchangeBrowseState.Loading)
        disposable = flowableExchangeRates.execute().subscribe({
            ratesLiveData.postValue(ExchangeBrowseState.Success(it))
        }, {
            ratesLiveData.postValue(ExchangeBrowseState.Error(it.message))
        })
    }
}