package com.xxmassdeveloper.mpchartexample;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman Petrashkevich on 20/11/2017.
 * For project Charts.
 */

public class GenieWeeklyBarActivity extends DemoBase {

    protected BarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genie_single);

        mChart = findViewById(R.id.chart);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(false);
        mChart.setClickable(false);
        mChart.getDescription().setEnabled(false);
        mChart.setMaxVisibleValueCount(60);
        mChart.setPinchZoom(false);
        mChart.setTouchEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBorders(false);
        mChart.setDrawMarkers(false);
        mChart.getAxisLeft().setDrawLabels(true);
        mChart.getAxisRight().setDrawLabels(true);
        mChart.getXAxis().setDrawLabels(true);
        mChart.getLegend().setEnabled(false);
        mChart.setViewPortOffsets(0f, 0f, 0f, 100f);
        Paint mPaint = mChart.getRenderer().getPaintRender();
        mPaint.setShader(new LinearGradient(0, 0, 0, 1000,
                Color.YELLOW, Color.parseColor("#ac0101"), Shader.TileMode.CLAMP));

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(2, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setSpaceTop(0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(11500f);
        leftAxis.setGranularity(10000f);
        leftAxis.setGridLineWidth(2);
        leftAxis.setTextSize(18);
        leftAxis.setXOffset(15);
        leftAxis.setDrawLabels(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setLabelCount(10, false);
        rightAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        rightAxis.setSpaceTop(0f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setAxisMaximum(11500f);
        rightAxis.setGranularity(1000f);
        rightAxis.setGridLineWidth(3);
        rightAxis.setTextSize(18);
        rightAxis.setXOffset(15);
        rightAxis.setYOffset(10);
        rightAxis.setDrawGridLines(false);
        rightAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value%10000 == 0 && value > 0 ? String.valueOf((int)value/1000) + "K" : "";
            }
        });

        List<Integer> steps = new ArrayList<>();
        steps.add(5672);
        steps.add(3000);
        steps.add(8000);
        steps.add(9500);
        steps.add(5000);
        steps.add(0);
        steps.add(0);
        setData(steps);
    }

    private void setData(List<Integer> steps) {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            yValues.add(new BarEntry(i, steps.get(i)));
        }

        BarDataSet dataSet;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            dataSet.setValues(yValues);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            dataSet = new BarDataSet(yValues, "Steps");
            dataSet.setDrawIcons(false);
            dataSet.setColors(ColorTemplate.MATERIAL_COLORS, 128);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            BarData data = new BarData(dataSets);
            data.setDrawValues(false);
            data.setBarWidth(0.5f);

            mChart.setData(data);
        }
    }

}