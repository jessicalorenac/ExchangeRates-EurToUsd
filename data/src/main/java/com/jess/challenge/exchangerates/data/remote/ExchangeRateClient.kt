package com.jess.challenge.exchangerates.remote

import com.jess.challenge.exchangerates.remote.model.ExchangeRateModel
import com.jess.challenge.exchangerates.remote.model.Rate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateClient{

    @GET("/history")
    fun getEuroExchangeRates(
        @Query("start_at") startDate: String,
        @Query("end_at") endDate: String,
        @Query("symbols") symbol: String
    ): Call<ExchangeRateModel>

    @GET("/latest")
    fun getLatestEuroRate(@Query("symbols") symbol: String): Call<Rate>

}