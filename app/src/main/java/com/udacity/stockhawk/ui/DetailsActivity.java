package com.udacity.stockhawk.ui;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.udacity.stockhawk.R;

import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;


public class DetailsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent graph = getIntent();
        setTitle(graph.getStringExtra("Symbol"));
        String[] str_points = graph.getStringArrayExtra("History");
        DataPoint[] dataPoints = new DataPoint[str_points.length];
        GraphView chart = (GraphView) findViewById(R.id.graph);
        Arrays.sort(str_points);
        for(int i=0;i< str_points.length;i++){
            String[] sub_points =str_points[i].split(Pattern.quote(","));
            long x = Long.parseLong(sub_points[0]);
            Double y = Double.parseDouble(sub_points[1]);
            dataPoints[i]= new DataPoint(new Date(x),y);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        chart.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(DetailsActivity.this));
        chart.addSeries(series);

    }

}
