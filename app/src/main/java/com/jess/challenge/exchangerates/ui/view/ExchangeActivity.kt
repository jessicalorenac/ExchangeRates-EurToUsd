package com.jess.challenge.exchangerates.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jess.challenge.exchangerates.data.repository.ExchangeRatesRepositoryImpl
import com.jess.challenge.exchangerates.domain.interactor.GetRangedRateList
import com.jess.challenge.exchangerates.domain.interactor.GetRecentRate
import com.jess.challenge.exchangerates.domain.model.DateRange
import com.jess.challenge.exchangerates.ui.R
import com.jess.challenge.exchangerates.ui.model.EuroChartModel
import com.jess.challenge.exchangerates.ui.viewmodel.ExchangeRatesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class ExchangeActivity : AppCompatActivity() {

    lateinit var exchangeRatesViewModel: ExchangeRatesViewModel

    val dateRange = DateRange("2018-04-21", "2018-04-26")
    val messageCurrentValue = "current value: € "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        exchangeRatesViewModel.chartDetail.observe(this, Observer { updateScreen(it) })
        exchangeRatesViewModel.loadViewData(dateRange)


    }

    private fun updateScreen(data: EuroChartModel) {

        currentValue.text = "$messageCurrentValue ${data.currentValue}"
        selectedRate.text = data.currentValue.toString()
        dateSelected.text = data.selectedDate
        valueMax.text = data.maxRate.toString()
        valueMin.text = data.minRate.toString()
        valueAvg.text = data.avgRate.toString()

    }

}
