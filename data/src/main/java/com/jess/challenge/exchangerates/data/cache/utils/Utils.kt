package com.jess.challenge.exchangerates.data.cache.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val FORMAT = "yyyy-MM-dd"

fun getLocalDate(stringDate: String) = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern(FORMAT))