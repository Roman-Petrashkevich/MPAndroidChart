package com.github.mikephil.charting.components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointF;

import java.lang.ref.WeakReference;

/**
 * Created by Roman Petrashkevich on 23/11/2017.
 * For project MPChartLib.
 */

public class MarkerText extends ComponentBase implements IMarker {
    private String mText;

    private MPPointF mOffset = new MPPointF();
    private MPPointF mOffset2 = new MPPointF();
    private WeakReference<Chart> mWeakChart;
    private Paint.Style mStyle = Paint.Style.FILL_AND_STROKE;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param text text to be displayed in the header
     */
    public MarkerText(String text) {
        mText = text;
    }

    public void setOffset(MPPointF offset) {
        mOffset = offset;

        if (mOffset == null) {
            mOffset = new MPPointF();
        }
    }

    public void setOffset(float offsetX, float offsetY) {
        mOffset.x = offsetX;
        mOffset.y = offsetY;
    }

    @Override
    public MPPointF getOffset() {
        return mOffset;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public void setChartView(Chart chart) {
        mWeakChart = new WeakReference<>(chart);
    }

    public Chart getChartView() {
        return mWeakChart == null ? null : mWeakChart.get();
    }

    private FSize getFSize() {
        Paint textPaint = getTextPaint();
        return new FSize(textPaint.measureText(mText), textPaint.getTextSize());
    }

    private Paint getTextPaint() {
        Paint textPaint = new Paint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(mStyle);
        textPaint.setPathEffect(null);
        textPaint.setColor(mTextColor);
        textPaint.setTypeface(mTypeface);
        textPaint.setStrokeWidth(0.5f);
        textPaint.setTextSize(mTextSize);

        return textPaint;
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        FSize size = getFSize();

        MPPointF offset = getOffset();
        mOffset2.x = offset.x;
        mOffset2.y = offset.y;

        Chart chart = getChartView();

        float width = size.width;
        float height = size.height;

        if (posX + mOffset2.x < 0) {
            mOffset2.x = - posX;
        } else if (chart != null && posX + width + mOffset2.x > chart.getWidth()) {
            mOffset2.x = chart.getWidth() - posX - width;
        }

        if (posY + mOffset2.y < 0) {
            mOffset2.y = - posY;
        } else if (chart != null && posY + height + mOffset2.y > chart.getHeight()) {
            mOffset2.y = chart.getHeight() - posY - height;
        }

        return mOffset2;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        if (mText == null) return;

        Paint textPaint = getTextPaint();

        FSize size = getFSize();
        MPPointF offset = getOffsetForDrawingAtPoint(posX, posY);

        float width = size.width;
        float height = size.height;

        int saveId = canvas.save();
        // translate to the correct position and draw
        canvas.translate(posX + offset.x, posY + offset.y);
        canvas.drawText(mText, -width/2, 0, textPaint);
        canvas.restoreToCount(saveId);

    }
}
