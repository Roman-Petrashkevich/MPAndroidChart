package com.xxmassdeveloper.mpchartexample;

import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerText;
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
import java.util.List;

/**
 * Created by Roman Petrashkevich on 20/11/2017.
 * For project Charts.
 */

public class GenieMultiBarActivity extends DemoBase {

    private int PARAM_GOAL = 5000;

    private static float CONST_VIEW_PORT_OFFSET_TOP = 15f;
    private static float CONST_VIEW_PORT_OFFSET_BOTTOM = 30f;

    private static float CONST_AXIS_MAX_MULTIPLIER = 1.05f;
    private static float CONST_AXIS_MIN_MULTIPLIER = -0.05f;
    private static float CONST_AXIS_MAX_LABEL_MULTIPLIER = 0.95f;
    private static int   CONST_AXIS_MAX_NUMBER_OF_LABELS = 50;

    private static float CONST_GRANULARITY = 1000f;
    private static float CONST_MIN_RANGE = 10000f;

    private static float CONST_FONT_SIZE_STEPS = 15;
    private static float CONST_LINE_WIDTH = 2;
    private static float CONST_LINE_PADDING = 20;
    private static float CONST_OFFSET = 15;
    private static float CONST_MARKER_OFFSET = -15;
    private static float CONST_FONT_SIZE = 12;
    private static float CONST_BAR_WIDTH = 0.5f;
    private static int   CONST_WEEK_LENGTH = 7;
    private static float CONST_MIN_BAR_HEIGHT_MULT = 0.01f;
    private static float CONST_SPACE_WEEKLY_MIN = 0.7f;
    private static float CONST_SPACE_WEEKLY_MAX = 1.7f;
    private static int   CONST_SPACE_MONTHLY_MIN = 2;
    private static int   CONST_SPACE_MONTHLY_MAX = 5;

    protected BarChart chart;

    private DecimalFormat numberFormat = new DecimalFormat("###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genie_multiple);

        chart = findViewById(R.id.chart);

        initViews();

        List<Integer> weeklySteps = new ArrayList<>();
        weeklySteps.add(5672);
        weeklySteps.add(3000);
        weeklySteps.add(8000);
        weeklySteps.add(9999);
        weeklySteps.add(5000);
        weeklySteps.add(5000);
        weeklySteps.add(0);

        List<Integer> monthlySteps = new ArrayList<>();
        for(int i = 0; i < 31; i++) {
            monthlySteps.add((int)(Math.random() * 10000));
        }

        setWeeklyData(weeklySteps);
//        setMonthlyData(monthlySteps);
    }

    private void initViews() {
        Typeface boldFont = ResourcesCompat.getFont(this, R.font.gilroy_bold);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(false);
        chart.setTouchEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.setDrawMarkers(true);
        chart.getXAxis().setDrawLabels(true);
        chart.getAxisLeft().setDrawLabels(true);
        chart.getAxisRight().setDrawLabels(true);
        chart.setDrawAxisOnTopOfData(true);
        chart.setExtraOffsets(0, CONST_VIEW_PORT_OFFSET_TOP, 0, CONST_VIEW_PORT_OFFSET_BOTTOM);
        chart.setMinOffset(0);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceMin(CONST_SPACE_WEEKLY_MIN);
        xAxis.setSpaceMax(CONST_SPACE_WEEKLY_MAX);
        xAxis.setAxisLineWidth(CONST_LINE_WIDTH);
        xAxis.setTypeface(boldFont);
        xAxis.setTextSize(CONST_FONT_SIZE);
        xAxis.setHighLightColor(ContextCompat.getColor(this, R.color.genie_selection_color));
        xAxis.setTextColor(Color.WHITE);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(2, true);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setGridLineWidth(CONST_LINE_WIDTH);
        leftAxis.setDrawLabels(false);

        final YAxis rightAxis = chart.getAxisRight();
        rightAxis.setLabelCount(CONST_AXIS_MAX_NUMBER_OF_LABELS, false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        rightAxis.setGranularity(CONST_GRANULARITY);
        rightAxis.setXOffset(CONST_OFFSET);
        rightAxis.setYOffset(0);
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(boldFont);
        rightAxis.setTextSize(CONST_FONT_SIZE);
        rightAxis.setTextColor(Color.WHITE);
        rightAxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                float maxValueThreshold = axis.getAxisMaximum() * CONST_AXIS_MAX_LABEL_MULTIPLIER;

                boolean isFirstLabel = value == 0;
                boolean isLastLabel = maxValueThreshold < value &&
                        value - CONST_GRANULARITY < maxValueThreshold;

                return isFirstLabel ? "0" : isLastLabel ? formatAxisLabels(value) : "";
            }
        });

        LimitLine limitLine = new LimitLine(PARAM_GOAL, formatAxisLabels(PARAM_GOAL));
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.IMAGE_RIGHT);
        limitLine.setLineWidth(CONST_LINE_WIDTH);
        limitLine.setPaddingRight(CONST_LINE_PADDING);
        limitLine.setPaddingLeft(CONST_LINE_PADDING);
        limitLine.setLineColor(ContextCompat.getColor(this, R.color.genie_chart_limit_line));
        limitLine.setTextColor(ContextCompat.getColor(this, R.color.genie_chart_limit_line));
        limitLine.setTypeface(boldFont);
        limitLine.setTextSize(CONST_FONT_SIZE_STEPS);
        limitLine.setImageRes(R.drawable.np_trophy_888734_000000);
        leftAxis.addLimitLine(limitLine);
    }

    private void setWeeklyData(List<Integer> steps) {
        // TODO: Remove these temporary placeholders
        final List<String> tmpWeekDays = new ArrayList<>();
        tmpWeekDays.add("Mon");
        tmpWeekDays.add("Tue");
        tmpWeekDays.add("Wed");
        tmpWeekDays.add("Thurs");
        tmpWeekDays.add("Fri");
        tmpWeekDays.add("Sun");
        tmpWeekDays.add("Sat");
        int selectionIdx = 5;
        // ----------------------------------------------------

        float axisMax = getAxisMax(steps, CONST_GRANULARITY, CONST_MIN_RANGE);
        float axisMin = getAxisMin(axisMax);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setAxisMaximum(axisMax);
        rightAxis.setAxisMinimum(axisMin);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaximum(axisMax);
        leftAxis.setAxisMinimum(axisMin);

        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelCount(CONST_WEEK_LENGTH);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int day = (int)value;
                return day < tmpWeekDays.size() ? tmpWeekDays.get((int)value) : "";
            }
        });

        final float minYValue = axisMax * CONST_MIN_BAR_HEIGHT_MULT;
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            yValues.add(new BarEntry(i, (float)steps.get(i), minYValue));
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
            data.setBarWidth(CONST_BAR_WIDTH);

            chart.setData(data);
        }

        highlightEntry(selectionIdx, numberFormat.format(steps.get(selectionIdx)));
    }

    private void highlightEntry(int index, String markerText) {
        Typeface boldFont = ResourcesCompat.getFont(this, R.font.gilroy_bold);

        MarkerText marker = new MarkerText(markerText);
        marker.setOffset(0, CONST_MARKER_OFFSET);
        marker.setTextColor(ContextCompat.getColor(this, R.color.genie_selection_color));
        marker.setTypeface(boldFont);
        marker.setTextSize(CONST_FONT_SIZE);
        chart.setMarker(marker);
        chart.highlightValue(index, 0, 0);
    }

    private void setMonthlyData(final List<Integer> steps) {
        float axisMax = getAxisMax(steps, CONST_GRANULARITY, CONST_MIN_RANGE);
        float axisMin = getAxisMin(axisMax);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setAxisMaximum(axisMax);
        rightAxis.setAxisMinimum(axisMin);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaximum(axisMax);
        leftAxis.setAxisMinimum(axisMin);

        XAxis xAxis = chart.getXAxis();
        xAxis.setSpaceMin(CONST_SPACE_MONTHLY_MIN);
        xAxis.setSpaceMax(CONST_SPACE_MONTHLY_MAX);
        xAxis.setLabelCount(steps.size() + CONST_SPACE_MONTHLY_MIN + CONST_SPACE_MONTHLY_MAX);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int day = (int)value + 1;
                // TODO: Check Mondays properly
                return ((day+4)%CONST_WEEK_LENGTH == 0) && value <= steps.size() ? String.valueOf(day) : "";
            }
        });

        final float minYValue = axisMax * CONST_MIN_BAR_HEIGHT_MULT;
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            yValues.add(new BarEntry(i, (float)steps.get(i), minYValue));
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
            data.setBarWidth(0.5f);

            chart.setData(data);
        }
    }

    private String formatAxisLabels(float number) {
        int thousands = (int)number/1000;
        return thousands > 0 ? String.valueOf(thousands) + "K" : "0";
    }

    private int getAxisMax(List<Integer> data, float granularity, float minRange) {
        int max = 0;
        for (Integer item : data) {
            if (item > max) {
                max = item;
            }
        }
        float roundedMax = (int)((max / (int)granularity + 1) * granularity);
        return (int) (Math.max(roundedMax, minRange) * CONST_AXIS_MAX_MULTIPLIER);
    }

    private float getAxisMin(float axisMax) {
        return axisMax * CONST_AXIS_MIN_MULTIPLIER;
    }

}