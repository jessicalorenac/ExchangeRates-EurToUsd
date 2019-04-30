package com.jess.challenge.exchangerates.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jess.challenge.exchangerates.data.cache.EuroExchangeRateDatabase
import com.jess.challenge.exchangerates.data.cache.dao.ExchangeRateDao
import com.jess.challenge.exchangerates.data.cache.entities.ExchangeRateDbEntity
import com.jess.challenge.exchangerates.data.cache.utils.getLocalDate
import org.amshove.kluent.shouldEqual
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RunWith(AndroidJUnit4::class)
class EuroExchangeRateDbTest {

    private val START_DATE = "2019-03-01"
    private val END_DATE = "2019-03-24"
    private val LOCALDATE_START = getLocalDate(START_DATE)
    private val LOCALDATE_END = getLocalDate(END_DATE)
    private val RATE = 1.34F
    private val RATE2 = 1.142F
    private val LIST_DATES = listOf(
        "2019-03-01",
        "2019-03-02",
        "2019-03-03",
        "2019-03-04",
        "2019-03-05",
        "2019-03-06",
        "2019-03-07",
        "2019-03-08",
        "2019-03-09",
        "2019-03-10",
        "2019-03-11",
        "2019-03-12",
        "2019-03-13",
        "2019-03-14",
        "2019-03-15",
        "2019-03-16",
        "2019-03-17",
        "2019-03-18",
        "2019-03-19",
        "2019-03-20",
        "2019-03-21",
        "2019-03-22",
        "2019-03-23",
        "2019-03-24",
        "2019-03-25",
        "2019-03-26",
        "2019-03-27"
    )
    private val LIST_RATES = listOf(
        1.1233F, 1.1236F, 1.1243F, 1.1313F, 1.1279F, 1.1301F, 1.1264F, 1.125F, 1.12F, 1.1305F, 1.1305F,
        1.1321F, 1.1219F, 1.1277F, 1.1246F, 1.1233F, 1.1236F, 1.1243F, 1.1313F, 1.1279F, 1.1301F,
        1.1264F, 1.125F, 1.12F, 1.1305F, 1.1305F, 1.1233F
    )

    private val listExchangeRate =
        LIST_DATES.mapIndexed { index, date ->
            ExchangeRateDbEntity(
                null,
                getLocalDate(date),
                LIST_RATES[index]
            )
        }

    private lateinit var exchangeRateDao: ExchangeRateDao
    private lateinit var db: EuroExchangeRateDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, EuroExchangeRateDatabase::class.java).build()
        exchangeRateDao = db.getEuroExchangeRateDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeExchangeRateAndReadInList() {
        val exchangeRateEnd = ExchangeRateDbEntity(null, LOCALDATE_END, RATE)
        val exchangeRateStart = ExchangeRateDbEntity(null, LOCALDATE_START, RATE2)
        exchangeRateDao.insertRate(exchangeRateStart)
        exchangeRateDao.insertRate(exchangeRateEnd)
        val latestRate = exchangeRateDao.getMostRecentRate()

        latestRate.rateDate shouldEqual exchangeRateEnd.rateDate
        latestRate.rateVal shouldEqual exchangeRateEnd.rateVal
    }


    @Test
    @Throws(Exception::class)
    fun insertRangeRateList_RetrieveSmallerRangeRate() {
        exchangeRateDao.insertList(listExchangeRate)

        //In order to check DB is retrieving range data (with dates) as expected
        val listExchangeDb = exchangeRateDao.getRangedRates(getLocalDate(LIST_DATES[4]), getLocalDate(LIST_DATES[8]))

        //Lower limit from range selected
        listExchangeDb[0].rateDate shouldEqual listExchangeRate[4].rateDate
        listExchangeDb[0].rateVal shouldEqual listExchangeRate[4].rateVal

        //Upper limit from range selected
        listExchangeDb[4].rateDate shouldEqual listExchangeRate[8].rateDate
        listExchangeDb[4].rateVal shouldEqual listExchangeRate[8].rateVal

        }

    @Test
    @Throws(Exception::class)
    fun insertRangeList_getExchangeByDate(){
        exchangeRateDao.insertList(listExchangeRate)

        val exchangeRateDb = exchangeRateDao.getRateFromDate(getLocalDate(LIST_DATES[6]))

        exchangeRateDb.rateDate shouldEqual listExchangeRate[6].rateDate
        exchangeRateDb.rateVal shouldEqual listExchangeRate[6].rateVal
    }

    @Test
    @Throws(Exception::class)
    fun getNotExistingRate(){
        exchangeRateDao.insertList(listExchangeRate)

        val exchangeRateDb = exchangeRateDao.getRateFromDate(getLocalDate("2019-05-05"))

        exchangeRateDb shouldEqual null

    }


}