package com.example.taskassignmentpacedarshan.ui.main.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.taskassignmentpacedarshan.R
import com.example.taskassignmentpacedarshan.common.base.BaseFragment
import com.example.taskassignmentpacedarshan.databinding.FragmentDashboardBinding
import com.example.taskassignmentpacedarshan.ui.add_address.view.AddAddressActivity
import com.example.taskassignmentpacedarshan.ui.main.adapter.*
import com.example.taskassignmentpacedarshan.ui.main.viewmodel.MainFragmentViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import me.relex.circleindicator.CircleIndicator2
import org.koin.android.ext.android.inject
import java.util.*


class DashboardFragment :BaseFragment(){
    private val mainFragmentViewModel : MainFragmentViewModel by inject()
    private var binding : FragmentDashboardBinding?=null

    override fun getVm(): ViewModel {
     return mainFragmentViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init()
        if (binding != null){
            return binding?.root
        }
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard,container,false)
        binding?.mainFragmentViewModel = mainFragmentViewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        binding?.ivUserProfile?.setOnClickListener {
            startActivity(Intent(activity,AddAddressActivity::class.java))
        }

        initSettingsList()
        initInvestList()
        initNewsList()
        initResearchList()
        initRecentActivity()
        initChart()

        return binding?.root
    }

    private fun initSettingsList() {
        val adapter = SettingsAdapter()
        binding?.recycleSettings?.isNestedScrollingEnabled = false
        binding?.recycleSettings?.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding?.recycleSettings?.adapter = adapter

    }
    private fun initInvestList() {
        val adapter = InvestAdapter()
        binding?.recycleWhyInvest?.isNestedScrollingEnabled = false
        binding?.recycleWhyInvest?.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding?.recycleWhyInvest?.adapter = adapter

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding?.recycleWhyInvest)
        (binding?.indicator3 as CircleIndicator2).attachToRecyclerView(binding?.recycleWhyInvest!!, pagerSnapHelper)
        adapter.registerAdapterDataObserver((binding?.indicator3 as CircleIndicator2).adapterDataObserver)
    }
    private fun initNewsList() {
        val adapter = NewsAdapter()
        binding?.recycleNews?.isNestedScrollingEnabled = false
        binding?.recycleNews?.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding?.recycleNews?.adapter = adapter

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding?.recycleNews)
        (binding?.indicator as CircleIndicator2).attachToRecyclerView(binding?.recycleNews!!, pagerSnapHelper)
        adapter.registerAdapterDataObserver((binding?.indicator as CircleIndicator2).adapterDataObserver)
    }

    private fun initResearchList() {
        val adapter = ResearchAdapter()
        binding?.recycleResearch?.isNestedScrollingEnabled = false
        binding?.recycleResearch?.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding?.recycleResearch?.adapter = adapter

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding?.recycleResearch)
        (binding?.indicator2 as CircleIndicator2).attachToRecyclerView(binding?.recycleResearch!!, pagerSnapHelper)
        adapter.registerAdapterDataObserver((binding?.indicator2 as CircleIndicator2).adapterDataObserver)
    }

    private fun initRecentActivity() {
        val adapter = RecentActivityAdapter()
        binding?.recycleRecentActivity?.isNestedScrollingEnabled = false
        binding?.recycleRecentActivity?.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding?.recycleRecentActivity?.adapter = adapter
    }

    private fun initChart(){
        binding?.lineChart?.setViewPortOffsets(0f, 0f, 0f, 0f)
        binding?.lineChart?.setBackgroundColor(Color.rgb(104, 241, 175))

        // no description text

        // no description text
        binding?.lineChart?.description?.isEnabled = false

        // enable touch gestures

        // enable touch gestures
        binding?.lineChart?.setTouchEnabled(true)

        // enable scaling and dragging

        // enable scaling and dragging
        binding?.lineChart?.isDragEnabled = true
        binding?.lineChart?.setScaleEnabled(true)
        binding?.lineChart?.setBackgroundColor(Color.WHITE)

        // if disabled, scaling can be done on x- and y-axis separately

        // if disabled, scaling can be done on x- and y-axis separately
        binding?.lineChart?.setPinchZoom(false)

        binding?.lineChart?.setDrawGridBackground(false)
        binding?.lineChart?.maxHighlightDistance = 300f

        val x: XAxis? = binding?.lineChart?.xAxis
        x?.isEnabled = false

        val y: YAxis? = binding?.lineChart?.axisLeft
        y?.isEnabled =false
        y?.setLabelCount(6, false)
        y?.textColor = Color.GREEN
        y?.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        y?.setDrawGridLines(false)
        y?.axisLineColor = Color.GREEN

        binding?.lineChart?.axisRight?.isEnabled = false
        binding?.lineChart?.legend?.isEnabled = false

        binding?.lineChart?.animateXY(2000, 2000)

        // don't forget to refresh the drawing

        // don't forget to refresh the drawing
        binding?.lineChart?.invalidate()
        setData(45,100f)
    }

    private fun setData(count: Int, range: Float) {
        val values = ArrayList<Entry>()
        for (i in 0 until count) {
            val `val` = (Math.random() * (range + 1)).toFloat() + 5
            values.add(Entry(i.toFloat(), `val`))
        }
        val set1: LineDataSet
        if (binding?.lineChart?.data != null &&
            binding?.lineChart?.data?.dataSetCount!! > 0
        ) {
            set1 = binding?.lineChart?.data?.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            binding?.lineChart?.data?.notifyDataChanged()
            binding?.lineChart?.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.cubicIntensity = 0.2f
            set1.setDrawFilled(true)
            set1.setDrawCircles(false)
            set1.lineWidth = 1.8f
            set1.circleRadius = 4f
            set1.setCircleColor(Color.WHITE)
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.color = ContextCompat.getColor(context!!,R.color.chart_bg)
            set1.fillColor = ContextCompat.getColor(context!!,R.color.chart_bg_fill)
            set1.fillAlpha = 100
            set1.setDrawHorizontalHighlightIndicator(false)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> binding?.lineChart?.axisLeft?.axisMinimum!! }

            // create a data object with the data sets
            val data = LineData(set1)
            data.setValueTextSize(9f)
            data.setDrawValues(false)

            // set data
            binding?.lineChart?.data = data
        }
    }
}