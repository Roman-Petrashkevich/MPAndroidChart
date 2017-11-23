
package com.github.mikephil.charting.data;

import android.graphics.Color;

import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.utils.GradientColor;

import java.util.List;

/**
 * Baseclass of all DataSets for Bar-, Line-, Scatter- and CandleStickChart.
 * 
 * @author Philipp Jahoda
 */
public abstract class BarLineScatterCandleBubbleDataSet<T extends Entry> extends DataSet<T> implements IBarLineScatterCandleBubbleDataSet<T> {

    /** default highlight color */
    protected int mHighLightColor = Color.rgb(255, 187, 115);

    protected GradientColor mHighLightGradient;

    public BarLineScatterCandleBubbleDataSet(List<T> yVals, String label) {
        super(yVals, label);
    }

    /**
     * Sets the color that is used for drawing the highlight indicators. Dont
     * forget to resolve the color using getResources().getColor(...) or
     * Color.rgb(...).
     * 
     * @param color
     */
    public void setHighLightColor(int color) {
        mHighLightColor = color;
    }

    @Override
    public int getHighLightColor() {
        return mHighLightColor;
    }

    @Override
    public GradientColor getHighLightGradient() {
        return mHighLightGradient;
    }

    /**
     * Sets the gradient that is used for drawing the highlight indicators.
     * Set to null if you want the regular color to be used instead.
     *
     * @param gradient
     */
    public void setHighLightGradient(GradientColor gradient) {
        this.mHighLightGradient = gradient;
    }
}
