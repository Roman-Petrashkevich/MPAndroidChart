package com.xxmassdeveloper.mpchartexample;

import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerImage;
import com.github.mikephil.charting.components.MarkerText;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.GradientColor;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Roman Petrashkevich on 20/11/2017.
 * For project Charts.
 */

public class GenieMultiBarActivity extends DemoBase {

    private int PARAM_GOAL = 5000;

    private static float CONST_UPPER_BOUND_MULT = 2f;
    private static float CONST_LOWER_BOUND_MULT = -0.1f;
    private static int CONST_GRANULARITY_LABELS = 10000;
    private static int CONST_ANIMATION_TIME = 1500;
    private static int CONST_FONT_SIZE = 15;
    private static int CONST_LINE_WIDTH = 2;
    private static int CONST_GRID_LENGTH = 32;
    private static float CONST_GRID_LABEL_OFFSET_MULT = 0.5f;

    protected BarChart chart;

    private DecimalFormat numberFormat = new DecimalFormat("###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genie_multiple);

        chart = findViewById(R.id.chart);

        initViews();
    }

    private void initViews() {
        Typeface boldFont = ResourcesCompat.getFont(this, R.font.gilroy_bold);
        final float yAxisMax = PARAM_GOAL * CONST_UPPER_BOUND_MULT;
        final float yAxisMin = PARAM_GOAL * CONST_LOWER_BOUND_MULT;
        final float granularity = Math.abs(yAxisMin);
        int labelsCount = (int)(yAxisMax / granularity);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(false);
        chart.setTouchEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawMarkers(true);
        chart.getXAxis().setDrawLabels(true);
        chart.getAxisLeft().setDrawLabels(true);
        chart.getAxisRight().setDrawLabels(true);
        chart.setDrawAxisOnTopOfData(true);
        chart.setViewPortOffsets(0f, 50f, 0f, 50f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        xAxis.setAxisLineWidth(2f);
        xAxis.setTypeface(boldFont);
        xAxis.setTextSize(12);
        xAxis.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(2, true);
        leftAxis.setSpaceTop(0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setAxisMinimum(yAxisMin);
        leftAxis.setAxisMaximum(yAxisMax);
        leftAxis.setGranularity(granularity);
        leftAxis.setGridLineWidth(2);
        leftAxis.setTextSize(18);
        leftAxis.setXOffset(15);
        leftAxis.setDrawLabels(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setLabelCount(labelsCount, false);
        rightAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        rightAxis.setAxisMinimum(yAxisMin);
        rightAxis.setAxisMaximum(yAxisMax);
        rightAxis.setGranularity(granularity);
        rightAxis.setDrawGridLines(false);
        rightAxis.setXOffset(15);
        rightAxis.setYOffset(0);
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(boldFont);
        rightAxis.setTextSize(12);
        rightAxis.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        rightAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                boolean isFirstLabel = value == 0;
                boolean isLastLabel = value == yAxisMax;
                boolean isPreLastLabel = !isLastLabel && value + 2 * granularity > yAxisMax;

                return isFirstLabel ? "0" : isPreLastLabel ? formatAxisLabels(yAxisMax) : "";
            }
        });

        // TODO: Create a different limit line
        LimitLine limitLine = new LimitLine(PARAM_GOAL,
                getString(R.string.genie_poc_label_goal) + ": " + formatAxisLabels(PARAM_GOAL));
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.IMAGE_CENTER);
        limitLine.setLineWidth(CONST_LINE_WIDTH);
        limitLine.setLineColor(ContextCompat.getColor(this, R.color.genie_chart_limit_line));
        limitLine.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        limitLine.setTypeface(boldFont);
        limitLine.setTextSize(CONST_FONT_SIZE);
        limitLine.setImageRes(R.drawable.np_trophy_888734_000000);
        leftAxis.addLimitLine(limitLine);

        List<Integer> steps = new ArrayList<>();
        steps.add(5672);
        steps.add(3000);
        steps.add(8000);
        steps.add(9500);
        steps.add(5000);
        steps.add(1500);
        steps.add(2500);
        setWeeklyData(steps);
    }

    private void setWeeklyData(List<Integer> steps) {
        Typeface boldFont = ResourcesCompat.getFont(this, R.font.gilroy_bold);

        // TODO: Remove these temporary placeholders
        final List<String> tmpWeekDays = new ArrayList<>();
        tmpWeekDays.add("Mon");
        tmpWeekDays.add("Tue");
        tmpWeekDays.add("Wed");
        tmpWeekDays.add("Thurs");
        tmpWeekDays.add("Fri");
        tmpWeekDays.add("Sun");
        tmpWeekDays.add("Sat");
        int selectionIdx = 6;


        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelCount(8);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int day = (int)value;
                return day < tmpWeekDays.size() ? tmpWeekDays.get((int)value) : "";
            }
        });

        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (i < steps.size()) {
                yValues.add(new BarEntry(i, steps.get(i)));
            } else {
                // Add empty bars in case weekly data is incomplete
                yValues.add(new BarEntry(i, 0));
            }
        }

        BarDataSet dataSet;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) chart.getData().getDataSetByIndex(0);
            dataSet.setValues(yValues);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            dataSet = new BarDataSet(yValues, getString(R.string.genie_poc_label_steps));
            dataSet.setDrawIcons(false);
            dataSet.setColor(ContextCompat.getColor(this, R.color.genie_chart_gray));

            dataSet.setHighLightAlpha(255);
            dataSet.setHighLightGradient(new GradientColor(
                    ContextCompat.getColor(this, R.color.genie_chart_gradient_start),
                    ContextCompat.getColor(this, R.color.genie_chart_gradient_end),
                    Shader.TileMode.CLAMP
            ));

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            BarData data = new BarData(dataSets);
            data.setDrawValues(false);
            data.setBarWidth(0.5f);

            chart.setData(data);
        }

        // Highlight today's bar
        MarkerText marker = new MarkerText(numberFormat.format(yValues.get(selectionIdx).getY()));
        marker.setOffset(0, -15);
        marker.setTextColor(ContextCompat.getColor(this, R.color.genie_selection_color));
        marker.setTypeface(boldFont);
        marker.setTextSize(CONST_FONT_SIZE);
        chart.setMarker(marker);
        chart.highlightValue(selectionIdx, 0, 0);
    }

//    private void setMonthlyData(List<Integer> steps) {
//        final List<String> tmpWeekDays = new ArrayList<>();
//        tmpWeekDays.add("Mon");
//        tmpWeekDays.add("Tue");
//        tmpWeekDays.add("Wed");
//        tmpWeekDays.add("Thurs");
//        tmpWeekDays.add("Fri");
//        tmpWeekDays.add("Sun");
//        tmpWeekDays.add("Sat");
//
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setLabelCount(8);
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                int day = (int)value;
//                return day < tmpWeekDays.size() ? tmpWeekDays.get((int)value) : "";
//            }
//        });
//
//        ArrayList<BarEntry> yValues = new ArrayList<>();
//        for (int i = 0; i < 8; i++) {
//            if (i < steps.size()) {
//                yValues.add(new BarEntry(i, steps.get(i)));
//            } else {
//                // Add empty bars in case weekly data is incomplete
//                yValues.add(new BarEntry(i, 0));
//            }
//        }
//
//        BarDataSet dataSet;
//
//        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
//            dataSet = (BarDataSet) chart.getData().getDataSetByIndex(0);
//            dataSet.setValues(yValues);
//            chart.getData().notifyDataChanged();
//            chart.notifyDataSetChanged();
//        } else {
//            dataSet = new BarDataSet(yValues, getString(R.string.genie_poc_label_steps));
//            dataSet.setDrawIcons(false);
//
//            List<GradientColor> gradients = new ArrayList<>();
//            gradients.add(
//                    new GradientColor(
//                            ContextCompat.getColor(this, R.color.genie_chart_gradient_start),
//                            ContextCompat.getColor(this, R.color.genie_chart_gradient_end),
//                            Shader.TileMode.CLAMP
//                    ));
//            dataSet.setUseGradients(true);
//            dataSet.setGradients(gradients);
//
//            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//            dataSets.add(dataSet);
//
//            BarData data = new BarData(dataSets);
//            data.setDrawValues(false);
//            data.setBarWidth(0.5f);
//
//            chart.setData(data);
//        }
//    }

    private String formatAxisLabels(float number) {
        int thousands = (int)number/1000;
        return thousands > 0 ? String.valueOf(thousands) + "K" : "0";
    }

}