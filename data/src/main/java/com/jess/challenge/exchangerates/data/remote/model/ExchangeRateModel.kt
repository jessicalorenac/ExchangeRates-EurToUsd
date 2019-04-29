package com.jess.challenge.exchangerates.remote.model

data class ExchangeRateModel(
    val dateStart: String,
    val dateEnd: String,
    val listRate: List<Rate>
)