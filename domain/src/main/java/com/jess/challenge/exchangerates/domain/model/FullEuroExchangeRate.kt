package com.jess.challenge.exchangerates.domain.model

class FullEuroExchangeRate(val listRates: List<EuroExchangeEntity>,
                           val dateStart: String,
                           val dateEnd: String,
                           val minRate: Float,
                           val maxRate: Float,
                           val avgRate: Float) {
    companion object {
        const val NO_DATE_START = "No date found"
        const val NO_DATE_END = "No date found"
        fun empty() = FullEuroExchangeRate(emptyList(),
            NO_DATE_START,
            NO_DATE_END,
            0.toFloat(),
            0.toFloat(),
            0.toFloat())
    }
}