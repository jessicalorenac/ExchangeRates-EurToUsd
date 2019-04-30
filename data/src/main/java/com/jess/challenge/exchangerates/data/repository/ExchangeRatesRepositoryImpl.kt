package com.jess.challenge.exchangerates.data.repository

import android.content.Context
import android.util.Log
import com.jess.challenge.exchangerates.data.cache.EuroExchangeRateDatabase
import com.jess.challenge.exchangerates.data.cache.utils.getLocalDate
import com.jess.challenge.exchangerates.data.mapper.*
import com.jess.challenge.exchangerates.domain.Either
import com.jess.challenge.exchangerates.domain.Either.Left
import com.jess.challenge.exchangerates.domain.Either.Right
import com.jess.challenge.exchangerates.domain.exception.Failure
import com.jess.challenge.exchangerates.domain.model.DateRange
import com.jess.challenge.exchangerates.domain.model.FullEuroExchangeRate
import com.jess.challenge.exchangerates.domain.repository.ExchangeRatesRepository
import com.jess.challenge.exchangerates.remote.ExchangeServiceFactory

/**
 * Provides an implementation of the [ExchangeRatesRepository] interface for communicating to and from
 * data sources
 */
class ExchangeRatesRepositoryImpl(private val context: Context) : ExchangeRatesRepository {

    companion object{
        const val TAG = "ExchangeRatesRepositoryImpl"
    }

    private val databaseDao by lazy {
        EuroExchangeRateDatabase.getInstance(context)?.getEuroExchangeRateDao()
    }
    private val service = ExchangeServiceFactory.getExchangeService()

    //Mappers
    private val mapFromRemoteToDB = MapFromRemoteToDB()
    private val mapFromRemoteToModel = MapFromRemoteToModel()
    private val mapFromListDbToModel = MapFromListDbToListModel()
    private val mapFromRemoteToListDb = MapFromRemoteToListDb()
    private val mapFromRemoteToListModel = MapFromRemoteToListModel()

    override fun getMostRecentRate() =
        try {
            service.getLatestEuroRate().execute().run {
                when {
                    isSuccessful -> body()?.run {
                        try {
                            databaseDao?.insertRate(mapFromRemoteToDB.mapModel(this))
                        } catch (e: Exception) {
                            Log.e(TAG, "Error on insert rate to db: ${e.message}")
                        }
                        Right(mapFromRemoteToModel.mapModel(this))
                    } ?: Left(Failure.ServerError("No body"))

                    else -> Left(Failure.ServerError(errorBody()?.toString() ?: "Unknown error"))
                }
            }
        } catch (e: Exception) {
            Left(Failure.ServerError("Error getting Rate: ${e.message}"))
        }

    override fun getRangedRateList(dateRange: DateRange) =
        try {
            val startDate = getLocalDate(dateRange.startDate)
            val endDate = getLocalDate(dateRange.endDate)
            if (databaseDao?.getRateFromDate(startDate) != null && databaseDao?.getRateFromDate(endDate) != null) {
                Right(mapFromListDbToModel.mapModel(databaseDao?.getRangedRates(startDate, endDate) ?: listOf()))
            } else {
                service.getEuroExchangeRates(dateRange.startDate, dateRange.endDate).execute().run {
                    when {
                        isSuccessful -> body()?.run {
                            try {
                                databaseDao?.insertList(mapFromRemoteToListDb.mapModel(this))
                            } catch (e: Exception) {
                                Log.e(TAG, "Error on insert rate to db: ${e.message}")
                            }
                            Right(mapFromRemoteToListModel.mapModel(this))
                        } ?: Left(Failure.ServerError("No body"))

                        else -> Left(Failure.ServerError(errorBody()?.toString() ?: "Unknown error"))
                    }
                }
            }
        } catch (e: Exception) {
            Left(Failure.DBError("Error getting data from Db: ${e.message}"))
        }


    override fun getRangedFullRate(dateRange: DateRange): Either<Failure, FullEuroExchangeRate> {
        TODO("not needed yet") //To change body of created functions use File | Settings | File Templates.

    }

}