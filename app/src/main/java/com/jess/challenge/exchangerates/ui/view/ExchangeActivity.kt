package com.jess.challenge.exchangerates.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.tabs.TabLayout
import com.jess.challenge.exchangerates.domain.model.DateRange
import com.jess.challenge.exchangerates.ui.R
import com.jess.challenge.exchangerates.ui.model.CurrentValue
import com.jess.challenge.exchangerates.ui.model.EuroChartModel
import com.jess.challenge.exchangerates.ui.viewmodel.ExchangeRatesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDate

class ExchangeActivity : AppCompatActivity() {

    val exchangeRatesViewModel: ExchangeRatesViewModel by viewModel()

    lateinit var dateRange: DateRange
    val messageCurrentValue = "current value: € "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exchangeRatesViewModel.chartDetail.observe(this, Observer { updateScreen(it) })
        exchangeRatesViewModel.currentValue.observe(this, Observer { updateCurrentValue(it) })

        tabLayoutDateRanges.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                when {
                    tabLayoutDateRanges.selectedTabPosition == 0 -> dateRange =
                        DateRange(getCurrentDate().minusDays(5).toString(), getCurrentDate().toString())
                    tabLayoutDateRanges.selectedTabPosition == 1 -> dateRange =
                        DateRange(getCurrentDate().minusDays(30).toString(), getCurrentDate().toString())
                    tabLayoutDateRanges.selectedTabPosition == 2 -> dateRange =
                        DateRange(getCurrentDate().minusDays(90).toString(), getCurrentDate().toString())
                    tabLayoutDateRanges.selectedTabPosition == 3 -> dateRange =
                        DateRange(getCurrentDate().minusDays(180).toString(), getCurrentDate().toString())
                    tabLayoutDateRanges.selectedTabPosition == 4 -> dateRange =
                        DateRange(getCurrentDate().minusYears(1).toString(), getCurrentDate().toString())
                }
                exchangeRatesViewModel.loadLiveData(dateRange)
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {}

            override fun onTabUnselected(p0: TabLayout.Tab?) {}
        })

        tabLayoutDateRanges.getTabAt(2)?.select()
    }

    override fun onResume() {
        super.onResume()
        exchangeRatesViewModel.loadCurrentValue()
        exchangeRatesViewModel.loadLiveData(dateRange)
    }

    private fun updateScreen(data: EuroChartModel) {
        val dataSet = LineDataSet(data.entries, "Jessies")
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()

        selectedRate.text = data.selectedValue.toString()
        dateSelected.text = data.selectedDate
        valueMax.text = data.maxRate.toString()
        valueMin.text = data.minRate.toString()
        valueAvg.text = data.avgRate.toString()

    }

    private fun updateCurrentValue(data: CurrentValue) {
        currentValue.text = "$messageCurrentValue ${data.currentVal}"
    }

    // Default format yyyy-MM-dd is the one required
    private fun getCurrentDate() = LocalDate.now()

}
