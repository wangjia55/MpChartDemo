package com.wqdata.net.chart;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout mChartContainer;
    private BarLineChartBase mPartChart;
    private ArrayList<TemperatureDatas> mPeriodTempItemList;
    private Handler mHandler =new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_start).setOnClickListener(this);
        findViewById(R.id.button_stop).setOnClickListener(this);
        findViewById(R.id.button_reset).setOnClickListener(this);

        mChartContainer = (LinearLayout)findViewById(R.id.linear_temperature_history_chart_container);
        mPeriodTempItemList=new ArrayList<TemperatureDatas>();
        mPartChart = new LineChartFactory(this, mPeriodTempItemList).create();//初始化阶段性体温数据图表
        mChartContainer.addView(mPartChart, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    }

    private Runnable mChartRunnable = new Runnable() {
        @Override
        public void run() {
            TemperatureDatas datas  = new TemperatureDatas();
            datas.setBabyId("");
            datas.setData(36 + new Random().nextInt(6));
            datas.setTime(System.currentTimeMillis());
            addEntry(datas);
            mHandler.postDelayed(mChartRunnable, 2000);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start:
                chartReset();
                mHandler.removeCallbacks(mChartRunnable);
                mHandler.postDelayed(mChartRunnable,1000);
                break;
            case R.id.button_reset:
                chartReset();
                break;
            case R.id.button_stop:
                mHandler.removeCallbacks(mChartRunnable);
                break;
        }
    }

    private void addEntry(TemperatureDatas temperatureDatas) {
        ChartData data = mPartChart.getData();
        if (data != null) {
            DataSet set = data.getDataSetByIndex(0);
            Date date=new Date(temperatureDatas.getTime());
            DateFormat format=new SimpleDateFormat("HH:mm");
            String time=format.format(date);
            data.addXValue(time);
            int count = data.getXValCount() ;
            data.addEntry(new Entry(temperatureDatas.getData(), set.getEntryCount()), 0);
            mPartChart.notifyDataSetChanged();
            mPartChart.postInvalidate();

            //以下是关键代码
            mPartChart.setScaleMinima((float) data.getXValCount() / 10f, 1f);
            mPartChart.moveViewToX(count);
        }
    }

    private void chartReset() {
        mPartChart=null;
        mPartChart=new LineChartFactory(this, mPeriodTempItemList).create();
        mChartContainer.removeAllViews();
        mChartContainer.addView(mPartChart);
        mHandler.removeCallbacks(mChartRunnable);
    }
}
