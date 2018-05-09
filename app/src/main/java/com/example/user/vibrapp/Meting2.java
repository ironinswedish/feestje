package com.example.user.vibrapp;

import java.io.Serializable;


/**
 * The persistent class for the meting database table.
 */

public class Meting2 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    public int getTijd() {
        return tijd;
    }

    public void setTijd(int tijd) {
        this.tijd = tijd;
    }

    private int tijd;

    private String x;

    private String y;

    private String z;

    private Experimenten experimenten;

    public Meting2() {
    }

    public Meting2(Meting2 meting) {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Experimenten getExperimenten() {
        return this.experimenten;
    }

    public void setExperimenten(Experimenten experimenten) {
        this.experimenten = experimenten;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

}