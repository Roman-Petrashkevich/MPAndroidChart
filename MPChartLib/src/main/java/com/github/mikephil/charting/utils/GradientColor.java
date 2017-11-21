package com.github.mikephil.charting.utils;

import android.graphics.Shader;

/**
 * Created by Roman Petrashkevich on 21/11/2017.
 * For project MPChartLib.
 */

public class GradientColor {
    private int startColor;
    private int endColor;

    private Shader.TileMode tileMode;

    public GradientColor(int startColor, int endColor, Shader.TileMode tileMode) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.tileMode = tileMode;
    }

    public int getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public Shader.TileMode getTileMode() {
        return tileMode;
    }

    public void setTileMode(Shader.TileMode tileMode) {
        this.tileMode = tileMode;
    }
}
