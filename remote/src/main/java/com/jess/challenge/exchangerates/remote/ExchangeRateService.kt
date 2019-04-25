package com.jess.challenge.exchangerates.remote

import com.jess.challenge.exchangerates.remote.model.ExchangeRateModel
import io.reactivex.Flowable
import retrofit2.http.GET

interface ExchangeRateService{

    @GET("/history")
    fun getEuroExchangeRates(): Flowable<ExchangeRateModel>

}