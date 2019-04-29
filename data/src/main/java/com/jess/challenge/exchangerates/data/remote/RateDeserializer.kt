package com.jess.challenge.exchangerates.remote

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.jess.challenge.exchangerates.remote.model.ExchangeRateModel
import com.jess.challenge.exchangerates.remote.model.Rate
import java.lang.reflect.Type


class RateDeserializer: JsonDeserializer<Rate> {
    companion object {
        const val API_RATE = "rates"
        const val DATE = "date"
        const val DEFAULT_DATE = "2000-01-01"
        const val DEFAULT_SYMBOL = "USD"
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Rate {
        val jsonObject = json?.asJsonObject
        return Rate(jsonObject?.get(DATE)?.asString ?: DEFAULT_DATE,jsonObject?.get(API_RATE)?.asJsonObject?.get(
            DEFAULT_SYMBOL)?.asFloat ?: 0.0F)
    }
}