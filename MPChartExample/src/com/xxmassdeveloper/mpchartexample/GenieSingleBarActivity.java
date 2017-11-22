package com.xxmassdeveloper.mpchartexample;

import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.TextView;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman Petrashkevich on 20/11/2017.
 * For project Charts.
 */

public class GenieSingleBarActivity extends DemoBase {

    private int PARAM_GOAL = 5000;
    private int PARAM_STEPS = 7500;

    private static float CONST_UPPER_BOUND_MULT = 2.3f;
    private static float CONST_LOWER_BOUND_MULT = -0.1f;
    private static int CONST_GRANULARITY = 1000;
    private static int CONST_GRANULARITY_LABELS = 10000;
    private static int CONST_ANIMATION_TIME = 700;
    private static int CONST_FONT_SIZE = 15;
    private static int CONST_LINE_WIDTH = 2;
    private static int CONST_GRID_LENGTH = 32;
    private static float CONST_GRID_LABEL_OFFSET_MULT = 0.5f;

    private BarChart chart;
    private TextView stepsLabel;

    private DecimalFormat numberFormat = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genie_single);

        stepsLabel = findViewById(R.id.tv_steps_count);
        chart = findViewById(R.id.chart);

        initViews();
    }

    private void initViews() {
        Typeface boldFont = ResourcesCompat.getFont(this, R.font.gilroy_bold);
        final float yAxisMax = PARAM_GOAL * CONST_UPPER_BOUND_MULT;
        final float yAxisMin = PARAM_GOAL * CONST_LOWER_BOUND_MULT;
        int labelsCount = (int)(yAxisMax / CONST_GRANULARITY);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(false);
        chart.setTouchEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.getXAxis().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setDrawLabels(true);
        chart.setDrawAxisOnTopOfData(true);
        chart.setClipToViewPortBottomEnabled(true);
        chart.setViewPortOffsets(0f, 0f, 0f, 0f);
        chart.animateY(CONST_ANIMATION_TIME);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(labelsCount, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setAxisMaximum(yAxisMax);
        leftAxis.setAxisMinimum(yAxisMin);
        leftAxis.setGranularity(CONST_GRANULARITY);
        leftAxis.setTypeface(boldFont);
        leftAxis.setTextSize(CONST_FONT_SIZE);
        leftAxis.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        leftAxis.setXOffset(CONST_GRID_LABEL_OFFSET_MULT * CONST_GRID_LENGTH);
        leftAxis.setGridLineWidth(CONST_LINE_WIDTH);
        leftAxis.setGridColor(ContextCompat.getColor(this, android.R.color.white));
        leftAxis.enableGridDashedLine(CONST_GRID_LENGTH, 1000000, 0);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                boolean isLastLabel = value + CONST_GRANULARITY > yAxisMax;
                boolean isPreLastLabel = value + 2 * CONST_GRANULARITY > yAxisMax && !isLastLabel;
                boolean isPreviousLabelShown = (value - CONST_GRANULARITY) % CONST_GRANULARITY_LABELS == 0;

                return value % CONST_GRANULARITY_LABELS == 0
                        || (isPreLastLabel && !isPreviousLabelShown)
                        ? numberFormat.format(value) : "";
            }
        });

        LimitLine limitLine = new LimitLine(PARAM_GOAL,
                getString(R.string.genie_poc_label_goal) + ": " + numberFormat.format(PARAM_GOAL));
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.IMAGE_CENTER);
        limitLine.setLineWidth(CONST_LINE_WIDTH);
        limitLine.setLineColor(ContextCompat.getColor(this, R.color.genie_chart_limit_line));
        limitLine.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        limitLine.setTypeface(boldFont);
        limitLine.setTextSize(CONST_FONT_SIZE);
        limitLine.setImageRes(R.drawable.np_trophy_888734_000000);
        leftAxis.addLimitLine(limitLine);

        setData(PARAM_STEPS);
    }

    private void setData(int steps) {
        String formatted = numberFormat.format(steps);
        stepsLabel.setText(formatted);

        ArrayList<BarEntry> yValues = new ArrayList<>();
        yValues.add(new BarEntry(0, steps));

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
            data.setBarWidth(1.0f);

            chart.setData(data);
        }
    }
}