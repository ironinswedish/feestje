package com.example.user.vibrapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class metingshow extends AppCompatActivity {
    public static Meting meting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metingshow);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        ArrayList<Float> xList = null;
        ArrayList<Float> yList = null;
        ArrayList<Float> zList = null;
        try {
            xList = getFloatArray(meting.getX());
            yList = getFloatArray(meting.getY());
            zList = getFloatArray(meting.getZ());
        } catch (IOException e) {

        }
        final DataPoint[] dpx = new DataPoint[xList.size()];
        final DataPoint[] dpy = new DataPoint[yList.size()];
        final DataPoint[] dpz = new DataPoint[zList.size()];
        for (int i = 0; i < xList.size(); i++) {
            dpx[i] = new DataPoint(i, xList.get(i));
            dpy[i] = new DataPoint(i, yList.get(i));
            dpz[i] = new DataPoint(i, zList.get(i));
        }
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(xList.size());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.removeAllSeries();
        LineGraphSeries seriesX = new LineGraphSeries<DataPoint>(dpx);
        final LineGraphSeries seriesY = new LineGraphSeries<DataPoint>(dpy);
        LineGraphSeries seriesZ = new LineGraphSeries<DataPoint>(dpz);
        seriesX.setColor(Color.GREEN);
        seriesX.setTitle("X");
        seriesY.setColor(Color.RED);
        seriesX.setTitle("Y");

        seriesZ.setColor(Color.BLUE);
        seriesX.setTitle("Z");
        seriesX.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                TextView x1 = (TextView) findViewById(R.id.xWaarde);
                x1.setText("x: " + String.valueOf(Math.round(1000*dataPoint.getY())/1000.0));
                TextView y1 = (TextView) findViewById(R.id.yWaarde);
                int a = (int) dataPoint.getX();
                y1.setText("y: " + String.valueOf(Math.round(dpy[a].getY()*1000)/1000.0));
                TextView z1 = (TextView) findViewById(R.id.zWaarde);
                z1.setText("z: " + String.valueOf(Math.round(dpz[a].getY()*1000)/1000.0));

            }
        });
        seriesY.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                TextView y1 = (TextView) findViewById(R.id.yWaarde);
                y1.setText("y: " + String.valueOf(Math.round(dataPoint.getY()*1000)/1000.0));
                TextView x1 = (TextView) findViewById(R.id.xWaarde);
                int a = (int) dataPoint.getX();
                x1.setText("x: " + String.valueOf(Math.round(1000*dpx[a].getY())/1000.0));
                TextView z1 = (TextView) findViewById(R.id.zWaarde);
                z1.setText("z: " + String.valueOf(Math.round(dpz[a].getY()*1000)/1000.0));


            }
        });
        seriesZ.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {


                TextView z1 = (TextView) findViewById(R.id.zWaarde);
                z1.setText("z: " + String.valueOf(Math.round(dataPoint.getY()*1000)/1000.0));
                TextView y1 = (TextView) findViewById(R.id.yWaarde);
                int a = (int) dataPoint.getX();
                y1.setText("y: " + String.valueOf(Math.round(dpy[a].getY()*1000)/1000.0));
                TextView x1 = (TextView) findViewById(R.id.xWaarde);
                x1.setText("x: " + String.valueOf(Math.round(1000*dpx[a].getY())/1000.0));


            }
        });

        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        graph.addSeries(seriesX);
        graph.addSeries(seriesY);
        graph.addSeries(seriesZ);
        Log.e("onCreate: ", graph.getSeries().get(0).getTitle());
        ;


    }

    private ArrayList<Float> getFloatArray(byte[] x) throws IOException {
        ByteArrayInputStream bas = new ByteArrayInputStream(x);
        DataInputStream ds = new DataInputStream(bas);
        ArrayList<Float> xList = new ArrayList<Float>();
        for (int i = 0; i < x.length / 4; i++) {
            xList.add(ds.readFloat());
        }
        return xList;

    }


}
