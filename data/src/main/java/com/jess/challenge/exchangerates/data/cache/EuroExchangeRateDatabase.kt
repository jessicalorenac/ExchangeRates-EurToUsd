package com.jess.challenge.exchangerates.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jess.challenge.exchangerates.data.cache.dao.ExchangeRateDao
import com.jess.challenge.exchangerates.data.cache.entities.ExchangeRateDbEntity


@Database(entities = arrayOf(ExchangeRateDbEntity::class), version = 2)
@TypeConverters(Converters::class)
abstract class EuroExchangeRateDatabase : RoomDatabase() {

    abstract fun getEuroExchangeRateDao(): ExchangeRateDao

    companion object {
        private var INSTANCE: EuroExchangeRateDatabase? = null

        fun getInstance(context: Context): EuroExchangeRateDatabase? {
            if (INSTANCE == null) {
                synchronized(EuroExchangeRateDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        EuroExchangeRateDatabase::class.java,
                        "euro_exchange.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}