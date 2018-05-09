package com.example.user.vibrapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;


/**
 * The persistent class for the meting database table.
 */
public class Meting implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    public Meting(JSONObject meting) {
        try {
            Log.e("Meting: ", meting.get("x").toString());
            // ArrayList<Float> xList=new ArrayList<Float>((Collection<? extends Float>) meting.getJSONArray("x"));
            ArrayList<Float> xList = stringToFloat(meting.get("x").toString());
            ArrayList<Float> yList = stringToFloat(meting.get("y").toString());
            ArrayList<Float> zList = stringToFloat(meting.get("z").toString());
            x = FloatArray2ByteArray(xList);
            y = FloatArray2ByteArray(yList);
            z = FloatArray2ByteArray(zList);
        } catch (JSONException e) {
            Log.e("Meting: ", "jsonexception");
        }
    }

    public int getTijd() {
        return tijd;
    }

    public void setTijd(int tijd) {
        this.tijd = tijd;
    }

    private int tijd;
    private byte[] x;
    private byte[] y;
    private byte[] z;
    private Experimenten experimenten;

    public Meting() {
    }

    public Meting(Meting meting) {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public byte[] getX() {
        return this.x;
    }

    public void setX(byte[] x) {
        this.x = x;
    }

    public byte[] getY() {
        return this.y;
    }

    public void setY(byte[] y) {
        this.y = y;
    }

    public byte[] getZ() {
        return this.z;
    }

    public void setZ(byte[] z) {
        this.z = z;
    }

    public Experimenten getExperimenten() {
        return this.experimenten;
    }

    public void setExperimenten(Experimenten experimenten) {

        this.experimenten = experimenten;
    }

    private ArrayList<Float> stringToFloat(String s) {
        ArrayList<Float> x = new ArrayList<Float>();
        int spatie = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                x.add(Float.parseFloat(s.substring(spatie + 1, i - 1)));
                spatie = i;
            } else if (s.charAt(i) == ']') {
                x.add(Float.parseFloat(s.substring(spatie + 1, i )));
                spatie = i;

            }
        }
        return x;
    }

    public static byte[] FloatArray2ByteArray(ArrayList<Float> values) {
        ByteBuffer buffer = ByteBuffer.allocate(4 * values.size());

        for (float value : values) {
            buffer.putFloat(value);
        }

        return buffer.array();
    }
}