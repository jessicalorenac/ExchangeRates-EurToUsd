package com.jess.challenge.exchangerates.domain

import com.jess.challenge.exchangerates.domain.exception.Failure
import com.jess.challenge.exchangerates.domain.exception.Failure.ServerError
import com.jess.challenge.exchangerates.domain.exception.Failure.DBError
import com.jess.challenge.exchangerates.domain.interactor.GetRecentRate
import com.jess.challenge.exchangerates.domain.interactor.UseCase
import com.jess.challenge.exchangerates.domain.model.EuroExchangeEntity
import com.jess.challenge.exchangerates.domain.repository.ExchangeRatesRepository
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetCurrentValueTest : AbstractUnitTest() {

    private val DATE = "2000-01-01"
    private val START_DATE = "2019-03-01"
    private val END_DATE = "2019-03-24"
    private val FORMAT = "yyyy-MM-dd"
    private val LOCALDATE_END = LocalDate.parse(END_DATE, DateTimeFormatter.ofPattern(FORMAT))
    private val RATE = 1.34.toFloat()

    @Mock
    private lateinit var exchangeRepository: ExchangeRatesRepository

    private lateinit var getCurrentValue: GetRecentRate

    @Before
    fun setup() {
        getCurrentValue = GetRecentRate(exchangeRepository)
        given { exchangeRepository.getMostRecentRate() }.willReturn(Either.Right(EuroExchangeEntity(LOCALDATE_END, RATE)))
    }

    @Test
    fun `running use case should call mockito repository only once`() {
        runBlocking { getCurrentValue.run(UseCase.None()) }

        verify(exchangeRepository).getMostRecentRate()
        verifyNoMoreInteractions(exchangeRepository)
    }

    @Test
    fun `running async use case should return Failure or current Rate`() {
        var result: Either<Failure, EuroExchangeEntity>? = null
        val euroExchangeEntity = EuroExchangeEntity(LOCALDATE_END, RATE)
        runBlocking { result = getCurrentValue(UseCase.None()).await() }
        result shouldNotBe null

        result?.either(
            { failure ->
                (failure is ServerError || failure is DBError) shouldEqual true
            },
            { success ->
                euroExchangeEntity.date shouldEqual success.date
                euroExchangeEntity.value shouldEqual success.value
            })

    }
}