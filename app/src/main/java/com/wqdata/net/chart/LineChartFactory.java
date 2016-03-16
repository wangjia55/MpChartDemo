package com.wqdata.net.chart;

import android.content.Context;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.BarLineChartTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bill on 2015/1/15.
 */
public class LineChartFactory extends AbstractBarLineChartFactory<LineData> {
    private static final float LINE_WIDTH = 1F;
    private static final float LINE_CIRCLE_SIZE = 3F;
    private static final int LINE_FILL_ALPHA = 50;
    private static final int LINE_COLOR = 0xFF03C5F2;
    private static final int FILL_COLOR = 0xFF03C5F2;
    private static final int FILL_HOLE_COLOR = 0xFFFFFFFF;

    public LineChartFactory(Context pContext, ArrayList<TemperatureDatas> pTempItemList) {
        super(pContext, pTempItemList);
    }
    public LineChartFactory(Context pContext, List<TemperatureDatas> pTempItemList, int pointCount, int timeSpan) {
        super(pContext, pTempItemList);
        mXAxisPointCount = pointCount;
        mTimeSpan = timeSpan;
    }

    @Override
    protected BarLineChartBase getChart() {
        LineChart chart = new LineChart(mContext);
        // Init custom marker view
        CustomMarkerView marker = new CustomMarkerView(mContext);
        chart.setDrawMarkerViews(true);
        chart.setMarkerView(marker);
        chart.setOnTouchListener(new CustomBarLineChartTouchListener(chart, chart.getViewPortHandler().getMatrixTouch()));
        return chart;
    }

    class CustomBarLineChartTouchListener extends BarLineChartTouchListener {

        public CustomBarLineChartTouchListener(BarLineChartBase pChart, Matrix pMatrix) {
            super(pChart, pMatrix);
        }

        @Override
        public boolean onTouch(View pView, MotionEvent pEvent) {
            return super.onTouch(pView, pEvent);
        }
    }

    @Override
    protected void initChart() {
    }

    @Override
    protected LineData getData() {
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        int size = mTempItemList.size();
        for (int i = 0; i < size; i++) {
            TemperatureDatas tempItem = mTempItemList.get(i);
            xVals.add(tempItem.getTime() % mTimeSpan == 0 ? DateUtils.formatHourMin(tempItem.getTime()) : "");
            yVals.add(new Entry(getFinalValue(tempItem.getData()), i));
        }

        LineDataSet set = new LineDataSet(yVals, "温度曲线");
        set.setColor(LINE_COLOR);
        set.setLineWidth(LINE_WIDTH);
        set.setCircleColor(LINE_COLOR);
        set.setCircleSize(LINE_CIRCLE_SIZE);
        set.setDrawFilled(true);
        set.setFillColor(FILL_COLOR);
        set.setFillAlpha(LINE_FILL_ALPHA);
        set.setDrawValues(false);
        set.setDrawCircleHole(true);
        set.setCircleColorHole(FILL_HOLE_COLOR);
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set);

        // Create a LineData
        return new LineData(xVals, dataSets);
    }

    /**
     * Ensure the value in a certain range [{@link #Y_AXIS_MIN_RANGE}, {@link #Y_AXIS_MAX_RANGE}]
     *
     * @param tem
     */
    private float getFinalValue(float tem) {
        if (tem < Y_AXIS_MIN_RANGE) {
            return Y_AXIS_MIN_RANGE;
        } else if (tem < Y_AXIS_MAX_RANGE) {
            return tem;
        } else {
            return Y_AXIS_MAX_RANGE;
        }
    }
}
