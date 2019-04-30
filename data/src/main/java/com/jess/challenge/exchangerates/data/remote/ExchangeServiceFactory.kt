package com.jess.challenge.exchangerates.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jess.challenge.exchangerates.data.remote.model.ExchangeRateModel
import com.jess.challenge.exchangerates.data.remote.model.Rate
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ExchangeServiceFactory {

    const val URL_REQUEST = "https://api.exchangeratesapi.io/"

    fun getExchangeService(): ExchangeRateClient {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL_REQUEST)
            .client(makeOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(makeGson()))
            .build()
        return retrofit.create(ExchangeRateClient::class.java)
    }


    private fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }


    private fun makeGson(): Gson {
        val gsonBuilder = GsonBuilder()
        val deserializer = ExchangeDeserializer()
        val rateDeserializer = RateDeserializer()
        gsonBuilder.registerTypeAdapter(Rate::class.java, rateDeserializer)
        gsonBuilder.registerTypeAdapter(ExchangeRateModel::class.java, deserializer)
        val gson = gsonBuilder.create()
        return gson
    }

}