package com.jess.challenge.exchangerates.data

import com.jess.challenge.exchangerates.data.remote.model.EuroDateRangeParams
import com.jess.challenge.exchangerates.data.remote.model.ExchangeRateModel
import com.jess.challenge.exchangerates.data.remote.model.Rate
import com.jess.challenge.exchangerates.remote.ExchangeServiceFactory
import org.junit.Test

class RemoteUnitTest {

    private val START_DATE = "2019-04-01"
    private val END_DATE = "2019-04-21"
    private val SYMBOL = "USD"

    private val requestParams = EuroDateRangeParams(START_DATE, END_DATE, SYMBOL)

    @Test
    fun `RateDeserializer should return a Rate`(){
        val result = ExchangeServiceFactory.getExchangeService().getLatestEuroRate(SYMBOL).execute()
        //Deserializer working as expected
        assert(result.isSuccessful && result.body() is Rate)
    }

    @Test
    fun `ExchangeDeserializer should return an ExchangeRate`() {
        val result = ExchangeServiceFactory.getExchangeService()
            .getEuroExchangeRates(requestParams.dateStart, requestParams.dateEnd, requestParams.symbol).execute()
        assert(result.isSuccessful && result.body() is ExchangeRateModel)
    }

}