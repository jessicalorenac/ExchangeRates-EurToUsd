package com.jess.challenge.exchangerates.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.jess.challenge.exchangerates.data.cache.entities.ExchangeRateDbEntity
import java.time.LocalDate

@Dao
interface ExchangeRateDao {


    @Query("SELECT * FROM exchangeRate WHERE date LIKE :date ")
    fun getRateFromDate(date: LocalDate): ExchangeRateDbEntity

    @Query("SELECT * FROM exchangeRate ORDER BY date DESC LIMIT 1")
    fun getMostRecentRate(): ExchangeRateDbEntity

    @Query("SELECT * FROM exchangeRate WHERE date BETWEEN :startDate AND :endDate")
    fun getRangedRates(startDate: LocalDate, endDate: LocalDate): List<ExchangeRateDbEntity>

    @Insert(onConflict = REPLACE)
    fun insertList(exchangeRate: List<ExchangeRateDbEntity>)

    @Insert(onConflict = REPLACE)
    fun insertRate(exchangeRate: ExchangeRateDbEntity)

    @Query("DELETE FROM exchangeRate")
    fun deleteAll()

}