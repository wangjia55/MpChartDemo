package com.wqdata.net.chart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;

/**
 * Created by bill on 2015/2/14.
 */
public class CustomMarkerView extends MarkerView {
	private static final int MARKER_OFFSET_Y = 10;
	private TextView mValueText;

	public CustomMarkerView(Context pContext) {
		super(pContext, R.layout.layout_chart_custom_marker_view);
		mValueText = (TextView) findViewById(R.id.sc_chart_custom_marker_tv);
	}

	// Callbacks EveryTime the MarkerView is redrawn, can be used to update the content
	@Override
	public void refreshContent(Entry pEntry, int pDataSetIndex) {
		if (pEntry instanceof CandleEntry) {
			CandleEntry ce = (CandleEntry) pEntry;
			mValueText.setText("" + ce.getHigh());
		} else {
			mValueText.setText("" + pEntry.getVal());
		}
	}

	@Override
	public int getXOffset() {
		// this will center the marker-view horizontally
		return -(getWidth() / 2);
	}

	@Override
	public int getYOffset() {
		// this will cause the marker-view to be above the selected value
		return -(getHeight() + UIUtils.dip2px(getContext(), MARKER_OFFSET_Y));
	}
}
