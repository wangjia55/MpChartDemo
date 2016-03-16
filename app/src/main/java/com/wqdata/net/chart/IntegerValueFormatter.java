package com.wqdata.net.chart;

import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.DecimalFormat;

public class IntegerValueFormatter implements ValueFormatter {

    private DecimalFormat mFormat;
    
    public IntegerValueFormatter() {
        mFormat = new DecimalFormat("00");   // 0, # means a digit (But # hide it if it's equal to zero)
    }
    
    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + "  ";
    }

}
