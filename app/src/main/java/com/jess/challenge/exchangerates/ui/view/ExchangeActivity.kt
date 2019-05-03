package com.jess.challenge.exchangerates.ui.view

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.jess.challenge.exchangerates.domain.exception.Failure
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
    var selectedTab = 2
    var TAB_SELECTED = "selectedTab"

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAB_SELECTED, selectedTab)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exchangeRatesViewModel.currentValue.observe(this, Observer { updateCurrentValue(it) })
        exchangeRatesViewModel.chartDetail.observe(this, Observer { updateScreen(it) })
        exchangeRatesViewModel.failure.observe(this, Observer { updateError(it) })

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
                selectedTab = tabLayoutDateRanges.selectedTabPosition
                showProgressBar(true)
                exchangeRatesViewModel.loadLiveData(dateRange)
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {}

            override fun onTabUnselected(p0: TabLayout.Tab?) {}
        })

        if(savedInstanceState != null){
            selectedTab = savedInstanceState.getInt(TAB_SELECTED)
        }

        tabLayoutDateRanges.getTabAt(selectedTab)?.select()



        lineChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {}

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                exchangeRatesViewModel.loadValueSelected(e)
            }
        })

        if (savedInstanceState == null) {
            showProgressBar(true)
            exchangeRatesViewModel.loadCurrentValue()
            exchangeRatesViewModel.loadLiveData(dateRange)
        }
    }

    private fun updateScreen(data: EuroChartModel) {
        showProgressBar(false)
        val dataSet = LineDataSet(data.entries, "USD")
        val lineData = LineData(dataSet)
        lineData.setValueTextColor(color(R.color.regularText))
        lineData.setValueTextSize(resources.getDimension(R.dimen.rate_chart_text_size))
        lineChart.data = lineData
        lineChart.animateX(1_000)
        lineChart.animateY(100, Easing.EaseOutElastic)
        lineChart.invalidate()

        selectedRate.text = data.selectedValue.toString()
        dateSelected.text = data.selectedDate
        valueMax.text = data.maxRate.toString()
        valueMin.text = data.minRate.toString()
        valueAvg.text = data.avgRate.toString()

    }

    private fun color(@ColorRes color: Int) = resources.getColor(color, null)

    private fun updateCurrentValue(data: CurrentValue) {
        currentValue.text = resources.getString(R.string.current_value_text, data.currentVal.toString())
        if (data.selectedVal != null) {
            selectedRate.text = data.selectedVal.toString()
            dateSelected.text = data.selectedDate
        }
    }

    private fun updateError(failure: Failure) {
        showProgressBar(false)
        Snackbar.make(
            constraintLayout,
            when (failure) {
                is Failure.DBError -> resources.getString(R.string.Error_no_data_found) + failure.message
                is Failure.ServerError -> resources.getString(R.string.Error_no_internet) + failure.message
                else -> resources.getString(R.string.Error)
            }, Snackbar.LENGTH_LONG
        ).show()
    }

    // Default format yyyy-MM-dd is the one required
    private fun getCurrentDate() = LocalDate.now()

    private fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }
}
