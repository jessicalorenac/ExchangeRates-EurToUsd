package com.jess.challenge.exchangerates.data.remote.model

//App is not supporting base changes, so values are not handling lists with their symbols and rates
data class Rate(val date: String, val value: Float)