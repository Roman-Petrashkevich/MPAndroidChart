package com.xxmassdeveloper.mpchartexample;

import android.graphics.Shader;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.GradientColor;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman Petrashkevich on 20/11/2017.
 * For project Charts.
 */

public class GenieSingleBarActivity extends DemoBase {

    protected BarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genie_single);

        mChart = findViewById(R.id.chart);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(false);
        mChart.setTouchEnabled(false);
        mChart.getDescription().setEnabled(false);
        mChart.getLegend().setEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.getXAxis().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setDrawLabels(true);
        mChart.setDrawAxisOnTopOfData(true);
        mChart.setClipToViewPortBottomEnabled(true);
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);
        mChart.animateY(1000);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(10, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setAxisMaximum(11500f);
        leftAxis.setAxisMinimum(-500f);
        leftAxis.setGranularity(1000f);
        leftAxis.setTextSize(18);
        leftAxis.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        leftAxis.setXOffset(25);
        leftAxis.setGridLineWidth(3);
        leftAxis.setGridColor(ContextCompat.getColor(this, android.R.color.white));
        leftAxis.enableGridDashedLine(50, 1000000, 0);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value%10000 == 0 ? String.valueOf((int)value) : "";
            }
        });

        LimitLine limitLine = new LimitLine(5000, "Goal: 5000");
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.IMAGE_CENTER);
        limitLine.setLineWidth(3);
        limitLine.setLineColor(ContextCompat.getColor(this, R.color.genie_chart_limit_line));
        limitLine.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        limitLine.setTextSize(15);
        limitLine.setImageRes(R.drawable.np_trophy_888734_000000);
        leftAxis.addLimitLine(limitLine);

        List<Integer> steps = new ArrayList<>();
        steps.add(7500);
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

            List<GradientColor> gradients = new ArrayList<>();
            gradients.add(
                    new GradientColor(
                            ContextCompat.getColor(this, R.color.genie_chart_gradient_start),
                            ContextCompat.getColor(this, R.color.genie_chart_gradient_end),
                            Shader.TileMode.CLAMP
                    ));
            dataSet.setUseGradients(true);
            dataSet.setGradients(gradients);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            BarData data = new BarData(dataSets);
            data.setDrawValues(false);
            data.setBarWidth(1.0f);

            mChart.setData(data);
        }
    }
}