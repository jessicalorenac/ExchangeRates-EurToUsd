package com.jess.challenge.exchangerates.remote

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.jess.challenge.exchangerates.data.remote.model.ExchangeRateModel
import com.jess.challenge.exchangerates.data.remote.model.Rate
import java.lang.reflect.Type


class ExchangeDeserializer: JsonDeserializer<ExchangeRateModel> {
    companion object {
        const val API_DATE_START = "start_at"
        const val API_DATE_END = "end_at"
        const val API_BASE = "base"
        const val API_RATE = "rates"
        const val DEFAULT_DATE = "2000-01-01"
        const val DEFAULT_SYMBOL = "USD"
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ExchangeRateModel {
        val jsonObject = json?.asJsonObject

        val rates = jsonObject?.get(API_RATE)?.asJsonObject?.entrySet()?.map {
            Rate(it.key, it.value.asJsonObject.get(DEFAULT_SYMBOL).asFloat)
        }.orEmpty()

        val exchangeRate = ExchangeRateModel (jsonObject?.get(API_DATE_START)?.asString ?: DEFAULT_DATE,
            jsonObject?.get(API_DATE_END)?.asString ?: DEFAULT_DATE, rates)

        return exchangeRate
    }
}