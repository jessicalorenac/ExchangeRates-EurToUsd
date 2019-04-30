package com.jess.challenge.exchangerates.ui.model

import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity

data class EuroChartModel(
    val exchangeRateList: List<EuroExchangeEntity>,
    val currentValue: Float,
    val selectedValue: Float,
    val selectedDate: String,
    val maxRate: Float,
    val minRate: Float,
    val avgRate: Float
)