package com.jess.challenge.exchangerates.data.cache

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { Instant.ofEpochSecond(it).atZone(ZoneId.systemDefault()).toLocalDate()}
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.atStartOfDay(ZoneId.systemDefault())?.toEpochSecond()
    }
}