package com.jess.challenge.exchangerates.data.remote.model


data class EuroDateRangeParams(val dateStart: String, val dateEnd: String, val symbol: String = "USD")