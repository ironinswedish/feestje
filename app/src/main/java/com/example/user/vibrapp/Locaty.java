package com.example.user.vibrapp;

import java.io.Serializable;


/**
 * The persistent class for the locaties database table.
 * 
 */
public class Locaty implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String adres;

	private float lat;

	private float lng;

	private String naam;

	private Project project;

	public Locaty() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdres() {
		return this.adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public float getLat() {
		return this.lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return this.lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}