package com.wqdata.net.chart;

import android.content.Context;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarLineScatterCandleData;
import com.github.mikephil.charting.data.BarLineScatterCandleDataSet;
import com.github.mikephil.charting.data.Entry;

import java.util.List;

/**
 * Created by bill on 2015/1/15.
 */
public abstract class AbstractBarLineChartFactory<T extends BarLineScatterCandleData<? extends BarLineScatterCandleDataSet<? extends Entry>>> implements IChartFactory {

	protected static final int Y_AXIS_MIN_RANGE = 35;
	protected static final int Y_AXIS_MAX_RANGE = 42;
	protected static final float Y_AXIS_OFFSET_RATIO = 0.1F;
	protected static final int VALUE_TEXT_SIZE = 15;
	protected static final float AXIS_DEFAULT_SCALE = 1F;
	protected static final float X_Y_LABELS_TEXT_SIZE = 13;
	protected static final int Y_LABELS_COUNT = 8;
	protected static final int BACKGROUND_COLOR = 0xFFFFFFFF;
	protected static final int GRID_COLOR = 0x4D03C5F2;
	protected static final int X_AXIS_LABEL_COLOR = 0xFFE0E0E0;
	protected static final int Y_AXIS_LABEL_COLOR = 0xFFE0E0E0;
	protected static final int LABELS_TO_SKIP = 0;
	protected static final int MAX_PAGE_DEFAULT_COUNT = 10;//每页默认的点数
	private static final int TIME_SPAN_DEFAULT_INTERVAL = 120000;//X轴的默认时间间隔
	protected Context mContext;
	protected BarLineChartBase mBarLineChartBase;
	protected List<TemperatureDatas> mTempItemList;
	protected int mXAxisPointCount;
	protected int mTimeSpan;

	public AbstractBarLineChartFactory(Context pContext, List<TemperatureDatas> pTempItemList) {
		mContext = pContext;
		mTempItemList = pTempItemList;
		mXAxisPointCount = MAX_PAGE_DEFAULT_COUNT;
		mTimeSpan = TIME_SPAN_DEFAULT_INTERVAL;
	}
	@Override
	public BarLineChartBase create() {
		mBarLineChartBase = getChart();
		initInside();
		initChart();
		return mBarLineChartBase;
	}

	private void initInside() {
		mBarLineChartBase.setBackgroundColor(BACKGROUND_COLOR);
		mBarLineChartBase.setDrawGridBackground(false);

		initYRange();
		// init minimum scale
		float xAxisScale =  (float) mTempItemList.size() / mXAxisPointCount;
		mBarLineChartBase.getViewPortHandler().setMinimumScaleX((xAxisScale < AXIS_DEFAULT_SCALE) ? AXIS_DEFAULT_SCALE : xAxisScale);      // 默认的缩放比（否则所有数据显示在一屏）
		mBarLineChartBase.getViewPortHandler().setMinimumScaleY(AXIS_DEFAULT_SCALE);
		mBarLineChartBase.setDoubleTapToZoomEnabled(false);
		mBarLineChartBase.setScaleEnabled(true);
		mBarLineChartBase.setScaleXEnabled(true);
		mBarLineChartBase.setScaleYEnabled(false);
		mBarLineChartBase.setMaxVisibleValueCount(6);

		mBarLineChartBase.setViewPortOffsets(60, 50,50,50);
		mBarLineChartBase.setHighlightEnabled(false);
		mBarLineChartBase.setData(getData());
		mBarLineChartBase.getLegend().setEnabled(false);    // You need to set data for the chart before calling this method
		mBarLineChartBase.setDescription("");

		// Setup X xAxis
		XAxis xAxis = mBarLineChartBase.getXAxis();
		xAxis.setTextSize(X_Y_LABELS_TEXT_SIZE);
		xAxis.setTextColor(X_AXIS_LABEL_COLOR);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		xAxis.setDrawGridLines(false);
		xAxis.setLabelsToSkip(LABELS_TO_SKIP);
		xAxis.setDrawAxisLine(false);
		xAxis.setAvoidFirstLastClipping(false);


		// Setup Y yAxis
		YAxis yAxis = mBarLineChartBase.getAxisLeft();
		yAxis.setTextSize(X_Y_LABELS_TEXT_SIZE);
		yAxis.setTextColor(Y_AXIS_LABEL_COLOR);
		yAxis.setLabelCount(Y_LABELS_COUNT);
		yAxis.setDrawGridLines(true);
		yAxis.setGridColor(GRID_COLOR);
		yAxis.setDrawAxisLine(false);
		yAxis.setValueFormatter(new IntegerValueFormatter());
		mBarLineChartBase.getAxisRight().setEnabled(false);

		mBarLineChartBase.animateY(1500);
	}

	/**
	 * Init the Y axis range
	 */
	private void initYRange() {
		int diff = Y_AXIS_MAX_RANGE - Y_AXIS_MIN_RANGE;
		YAxis yLabels = mBarLineChartBase.getAxisLeft();
		yLabels.setAxisMaxValue(Y_AXIS_MAX_RANGE + diff * Y_AXIS_OFFSET_RATIO);
		yLabels.setAxisMinValue(Y_AXIS_MIN_RANGE - diff * Y_AXIS_OFFSET_RATIO);
		yLabels.setStartAtZero(false);
	}

	/**
	 * Get the specify chart
	 * @return
	 */
	protected abstract BarLineChartBase getChart();

	/**
	 * Init the chart, call after the chart was created.
	 */
	protected abstract void initChart();

	/**
	 * Get the specify DataSet T
	 * @return
	 */
	protected abstract T getData();
}
