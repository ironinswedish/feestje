package com.example.user.vibrapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.SensorEventListener;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.hardware.*;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.*;
import com.jjoe64.graphview.series.Series.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    public static ArrayList<Float> xList;
    private ArrayList<Float> yList;
    private ArrayList<Float> zList;
    //  private ArrayList<Float> time;
    public static byte[] xB;
    public static byte[] yB;
    public static byte[] zB;
    public static byte[] timeB;
    public static JSONObject mainNode = new JSONObject();
    private float startx, starty, startz;
    private final long starttijd = System.currentTimeMillis();
    public static int hertz = 100;
    private LocationManager mLocationManager;
    public static Location l;
    int permission;
    private boolean meten = true;

    public static boolean timing;
    public static int hoelangMeten;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            if (l != location) {
                l = location;
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        xList = new ArrayList<Float>();
        yList = new ArrayList<Float>();
        zList = new ArrayList<Float>();
        zList = new ArrayList<Float>();
        // time = new ArrayList<Float>();
        //starttijd = System.currentTimeMillis();
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            int permission = 1000;
            ActivityCompat.requestPermissions((Activity) this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, permission);
            Log.e("requestSingleUpdate: ", "perm" + permission);

        } else {

            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }


        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1,
                1, mLocationListener);



        /*SingleShotLocationProvider.requestSingleUpdate(this,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        Log.e( "onNewLocationAvailable","" );
                        Log.e("Location", "my location is " + location.toString());
                    }
                });*/

    }


    public static byte[] FloatArray2ByteArray(ArrayList<Float> values) {
        ByteBuffer buffer = ByteBuffer.allocate(4 * values.size());

        for (float value : values) {
            buffer.putFloat(value);
        }

        return buffer.array();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;


        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER && meten) {
            float x = (float) (Math.round(event.values[0] * 1000) / 1000.0);
            float y = (float) (Math.round(event.values[1] * 1000) / 1000.0);
            float z = (float) (Math.round(event.values[2] * 1000) / 1000.0);
            if (xList.isEmpty()) {
                startx = x;
                starty = y;
                startz = z;
            }
            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > (1000 / hertz)) {

                lastUpdate = curTime;


                xList.add(x - startx);
                yList.add(y - starty);
                zList.add(z - startz);
                // time.add((float) (curTime-starttijd));
                GraphView graph = (GraphView) findViewById(R.id.graph);
                final DataPoint[] dpx = new DataPoint[Math.min(xList.size(), 5 * hertz)];
                final DataPoint[] dpy = new DataPoint[Math.min(xList.size(), 5 * hertz)];
                final DataPoint[] dpz = new DataPoint[Math.min(xList.size(), 5 * hertz)];
                for (int i = Math.max(0, xList.size() - 5 * hertz); i < xList.size(); i++) {
                    dpx[xList.size() >= 5 * hertz ? i - xList.size() + 5 * hertz : i] = new DataPoint((xList.size() >= 5 * hertz ? i - xList.size() + 5 * hertz : i) * 1.0 / hertz, xList.get(i));
                    dpy[yList.size() >= 5 * hertz ? i - yList.size() + 5 * hertz : i] = new DataPoint((yList.size() >= 5 * hertz ? i - yList.size() + 5 * hertz : i) * 1.0 / hertz, yList.get(i));
                    dpz[zList.size() >= 5 * hertz ? i - zList.size() + 5 * hertz : i] = new DataPoint((zList.size() >= 5 * hertz ? i - zList.size() + 5 * hertz : i) * 1.0 / hertz, zList.get(i));

                }
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(5);
                graph.getViewport().setXAxisBoundsManual(true);
                graph.removeAllSeries();
                LineGraphSeries seriesX = new LineGraphSeries<DataPoint>(dpx);
                LineGraphSeries seriesY = new LineGraphSeries<DataPoint>(dpy);
                LineGraphSeries seriesZ = new LineGraphSeries<DataPoint>(dpz);
                seriesX.setColor(Color.GREEN);
                seriesY.setColor(Color.RED);
                seriesZ.setColor(Color.BLUE);
                seriesX.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        TextView x1 = (TextView) findViewById(R.id.textView3);
                        x1.setText("x: " + String.valueOf(dataPoint.getY()));
                        TextView y1 = (TextView) findViewById(R.id.textView4);
                        int a = (int) dataPoint.getX();
                        y1.setText("y: " + String.valueOf(dpy[a].getY()));
                        TextView z1 = (TextView) findViewById(R.id.textView5);
                        z1.setText("z: " + String.valueOf(dpz[a].getY()));

                    }
                });
                seriesY.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        TextView y1 = (TextView) findViewById(R.id.textView4);
                        y1.setText("y: " + String.valueOf(dataPoint.getY()));
                        TextView x1 = (TextView) findViewById(R.id.textView3);
                        int a = (int) dataPoint.getX();
                        x1.setText("x: " + String.valueOf(dpx[a].getY()));
                        TextView z1 = (TextView) findViewById(R.id.textView5);
                        z1.setText("z: " + String.valueOf(dpz[a].getY()));


                    }
                });
                seriesZ.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        int a = (int) dataPoint.getX();
                        TextView z1 = (TextView) findViewById(R.id.textView5);
                        z1.setText("z: " + String.valueOf(dataPoint.getY()));
                        TextView y1 = (TextView) findViewById(R.id.textView4);
                        y1.setText("y: " + String.valueOf(dpy[a].getY()));
                        TextView x1 = (TextView) findViewById(R.id.textView3);
                        x1.setText("x: " + String.valueOf(dpx[a].getY()));


                    }
                });
                graph.addSeries(seriesX);
                graph.addSeries(seriesY);
                graph.addSeries(seriesZ);
            }
        }
        if (timing&&meten) {
            if (starttijd + 1000 * hoelangMeten <= System.currentTimeMillis()) {
                change();
            }
        }
    }


    public void change() {
        meten = false;
        xB = FloatArray2ByteArray(xList);
        yB = FloatArray2ByteArray(yList);
        zB = FloatArray2ByteArray(zList);
        // timeB = FloatArray2ByteArray(time);


        try {
            mainNode.put("x", xList.toString());
            mainNode.put("y", yList.toString());
            mainNode.put("z", zList.toString());
            mainNode.put("tijd", hertz);
        } catch (JSONException e) {

        }

        Log.e("change: ", mainNode.toString());
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);

    }

    public void meting(View view) throws Exception {
        xB = FloatArray2ByteArray(xList);
        yB = FloatArray2ByteArray(yList);
        zB = FloatArray2ByteArray(zList);
        // timeB = FloatArray2ByteArray(time);


        mainNode.put("x", xList.toString());
        mainNode.put("y", yList.toString());
        mainNode.put("z", zList.toString());
        mainNode.put("tijd", String.valueOf(hertz));
        Log.e("meting: ",mainNode.toString() );
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}


/*
class SingleShotLocationProvider {

    public static interface LocationCallback {
        public void onNewLocationAvailable(GPSCoordinates location);
    }

    // calls back to calling thread, note this is for low grain: if you want higher precision, swap the
    // contents of the else and if. Also be sure to check gps permission/settings are allowed.
    // call usually takes <10ms
    public static void requestSingleUpdate(final Context context, final LocationCallback callback) {
        Log.e("check", "requestSingleUpdate: " );
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {
            Log.e( "requestSingleUpdate: ","first loop" );
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                int permission = 0;
                ActivityCompat.requestPermissions((Activity) context, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, permission);
                Log.e( "requestSingleUpdate: ","perm" );
                return;
            }
            locationManager.requestSingleUpdate(criteria, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    callback.onNewLocationAvailable(new GPSCoordinates(location.getLatitude(), location.getLongitude()));
                    Log.e( "onLocationChanged: ",location==null?"null":location.toString() );
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.e("onStatusChanged: ","" );
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            }, null);
        } else {
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                locationManager.requestSingleUpdate(criteria, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        callback.onNewLocationAvailable(new GPSCoordinates(location.getLatitude(), location.getLongitude()));
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                    }
                }, null);
            }
        }
    }


    // consider returning Location instead of this dummy wrapper class
    public static class GPSCoordinates {
        public float longitude = -1;
        public float latitude = -1;

        public GPSCoordinates(float theLatitude, float theLongitude) {
            longitude = theLongitude;
            latitude = theLatitude;
        }

        public GPSCoordinates(double theLatitude, double theLongitude) {
            longitude = (float) theLongitude;
            latitude = (float) theLatitude;
        }
    }
}*/
