package com.jasjotsingh.alertness_index;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.ValueDataEntry;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import im.dacer.androidcharts.BarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvAlpha;
    public static final String TAG = "MainActivity";
    int i = 0;
    Button b ;
    Handler handler = new Handler();
    long ms = 152*1000;
    List<Long> ftimes = new ArrayList<>();
    List<Long> btimes = new ArrayList<>();
    BarChart barChart;
    //AnyChartView anyChartView;
    BarView barView,barView2;
    LineChart lineChart;
    ArrayList<String> f_data = new ArrayList<String>();
    ArrayList<String> b_data = new ArrayList<String>();
    ArrayList<String> c_data = new ArrayList<String>();
    List<BarEntry> entries = new ArrayList<BarEntry>();
    String A = "ASDEFASKDEFGHEFRHEFRHEFRHEFSDEFAKEFSEFJKDJEFVNGJEFBBEFNJEFGDNBJDJKBJKDGDEFEFASKDEFEFGHEFREFHEFRHEFREFHSDAEFKSEFJKEFDJEFVNGEFJBEFBEFNJGEFDNBEFJDJEFKEFBEF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAlpha = findViewById(R.id.tvAlpha);
        b= findViewById(R.id.buttonPress);
        b.setOnClickListener(this);
        barChart = findViewById(R.id.chart);
        //anyChartView = findViewById(R.id.any_chart_view);
        barView = findViewById(R.id.bar_view);
        barView2 = findViewById(R.id.bar_view2);
        lineChart = findViewById(R.id.reportingChart);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        //b.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selector));

        int a = A.length();

        new CountDownTimer(a*500,500) {
            @Override
            public void onTick(long millisUntilFinished) {
                ms = (a*500)-millisUntilFinished;
                tvAlpha.setText(String.valueOf(A.charAt(i)) );
                if (A.charAt(i)=='F'){
                    ftimes.add(ms);
                    f_data.add(Long.toString(ms));
                    c_data.add(Long.toString(ms));
                }
                i++;

                //Log.d(TAG, "onTick: "+ms);
                //Toast.makeText(MainActivity.this, Integer.toString(a), Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFinish() {
                tvAlpha.setVisibility(View.GONE);
                //chart.setVisibility(View.VISIBLE);
                Log.d(TAG, "onFinish: "+ftimes+btimes);
                //philjay();
                //anychart();
                //barchart();
                lineChartPhil();
            }
        }.start();

//        for(int i = 0;i<A.length();i++){
//            tvAlpha.setText(A.charAt(i));
////            int finalI = i;
////            handler.postDelayed(new Runnable() {
////                @Override
////                public void run() {
////
////                    //tvAlpha.setText(A.charAt(finalI));
////                }
////            }, 1000);
//        }

    }
    public void lineChartPhil(){
        lineChart.setVisibility(View.VISIBLE);
        XAxis xAxis = lineChart.getXAxis();
        YAxis leftAxis = lineChart.getAxisLeft();
        XAxis.XAxisPosition position = XAxis.XAxisPosition.BOTTOM;
        xAxis.setPosition(position);

        lineChart.getDescription().setEnabled(true);
        Description description = new Description();
        description.setText("Time in milliseconds");
        description.setTextSize(15f);

        LineDataSet lineDataSet1,lineDataSet2;
        List<Entry> lineEntries1 = new ArrayList<Entry>();
        List<Entry> lineEntries2 = new ArrayList<Entry>();
        for(int i = 0;i<ftimes.size();i++)lineEntries1.add(new Entry(ftimes.get(i),1));
        for(int i = 0;i<btimes.size();i++)lineEntries2.add(new Entry(btimes.get(i),1.01f));

        lineDataSet1 = new LineDataSet(lineEntries1, "F Appearance times");
        lineDataSet1.setColor(getApplicationContext().getResources().getColor(R.color.purple_200));
        lineDataSet2 = new LineDataSet(lineEntries2, "Button Press times");
        lineDataSet2.setColor(Color.BLUE);

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);
        LineData data = new LineData(dataSets);

//        ValueFormatter formatter = new ValueFormatter() {
//            @Override
//            public String getAxisLabel(float value, AxisBase axis) {
//                return c_data.get((int) value);
//            }
//        };
//        xAxis.setValueFormatter(formatter);
        lineChart.setData(data);
        leftAxis.setAxisMaximum(1.5f);
        leftAxis.setAxisMinimum(0.5f);
        //lineChart.setHorizontalScrollBarEnabled(true);
        lineChart.animate();
        lineChart.invalidate();

    }

    public void philjay(){
        barChart.setVisibility(View.VISIBLE);
        BarDataSet barDataSet1,barDataSet2;
        ArrayList<BarEntry> barEntries1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> barEntries2 = new ArrayList<BarEntry>();

        for(int i = 0;i<ftimes.size();i++)barEntries1.add(new BarEntry(ftimes.get(i),1));
        for(int i = 0;i<btimes.size();i++)barEntries2.add(new BarEntry(btimes.get(i),1));

        barDataSet1 = new BarDataSet(barEntries1, "F Appearance times");
        barDataSet1.setColor(getApplicationContext().getResources().getColor(R.color.purple_200));
        barDataSet2 = new BarDataSet(barEntries2, "Button Press times");
        barDataSet2.setColor(Color.BLUE);

        // below line is to add bar data set to our bar data.
        BarData data = new BarData(barDataSet1, barDataSet2);

        // after adding data to our bar data we
        // are setting that data to our bar chart.
        barChart.setData(data);

        // below line is to remove description
        // label of our bar chart.
        barChart.getDescription().setEnabled(false);

        // below line is to get x axis
        // of our bar chart.
        XAxis xAxis = barChart.getXAxis();

        // below line is to set value formatter to our x-axis and
        // we are adding our days to our x axis.
        //xAxis.setValueFormatter(new IndexAxisValueFormatter(days));

        // below line is to set center axis
        // labels to our bar chart.
        xAxis.setCenterAxisLabels(true);

        // below line is to set position
        // to our x-axis to bottom.
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // below line is to set granularity
        // to our x axis labels.
        xAxis.setGranularity(1);

        // below line is to enable
        // granularity to our x axis.
        xAxis.setGranularityEnabled(true);

        // below line is to make our
        // bar chart as draggable.
        barChart.setDragEnabled(true);

        // below line is to make visible
        // range for our bar chart.
        barChart.setVisibleXRangeMaximum(5);

        // below line is to add bar
        // space to our chart.
        float barSpace = 0.1f;

        // below line is use to add group
        // spacing to our bar chart.
        float groupSpace = 0.5f;

        // we are setting width of
        // bar in below line.
        data.setBarWidth(0.15f);

        // below line is to set minimum
        // axis to our chart.
        barChart.getXAxis().setAxisMinimum(0);

        // below line is to
        // animate our chart.
        barChart.animate();

        // below line is to group bars
        // and add spacing to it.
        barChart.groupBars(0, groupSpace, barSpace);

//        xAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return c_data.get((int) value);
//
//            }
//        });
        // below line is to invalidate
        // our bar chart.
        barChart.invalidate();
    }

    public void barchart(){
        List<String> diff_times = new ArrayList<>();
        if(btimes.size()<ftimes.size()){
            for(int i = btimes.size();i<ftimes.size();i++)btimes.add((long) ftimes.get(i) +2000);
        }
        for(int i = 0;i<ftimes.size();i++){
            diff_times.add(Long.toString(btimes.get(i) - ftimes.get(i)));
        }
        barView.setVisibility(View.VISIBLE);
        barView.setBottomTextList(f_data);
        ArrayList<Integer> barData = new ArrayList<Integer>();
        for(int i = 0;i<ftimes.size();i++)barData.add(Integer.parseInt(diff_times.get(i)));
        barView.setDataList(barData,1);

//        barView2.setVisibility(View.VISIBLE);
//        barView2.setBottomTextList(b_data);
//        ArrayList<Integer> barData2 = new ArrayList<Integer>();
//        for(int i = 0;i<btimes.size();i++)barData2.add(1);
//        barView2.setDataList(barData2,1);

    }
//    public void anychart(){
//        anyChartView.setVisibility(View.VISIBLE);
//        Cartesian cartesian = AnyChart.column();
//        List<DataEntry> data = new ArrayList<>();
//        for(int j = 0;j<ftimes.size();j++)
//                data.add(new ValueDataEntry(Double.toString(ftimes.get(j)),1));
//
//        Column column = cartesian.column(data);
//
//        column.tooltip()
//                .titleFormat("{%X}")
//                .position(Position.CENTER_BOTTOM)
//                .anchor(Anchor.CENTER_BOTTOM)
//                .offsetX(0d)
//                .offsetY(5d)
//                .format("${%Value}{groupsSeparator: }");
//
//        cartesian.animation(true);
//
//    }
    @Override
    public void onClick(View view) {
        btimes.add(ms);
        b_data.add(Long.toString(ms));
        c_data.add(Long.toString(ms));
        //Toast.makeText(this, Long.toString(ms), Toast.LENGTH_SHORT).show();
    }
}
