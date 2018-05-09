package com.example.user.vibrapp;

import java.io.Serializable;


/**
 * The persistent class for the foto database table.
 * 
 */
public class Foto implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private byte[] foto;

	private String foto_naam;

	private Experimenten experimenten;

	private Project project;

	public Foto() {
	}
	
	public Foto(byte[] f,String fn,Experimenten e,Project p) {
		foto=f; foto_naam=fn;experimenten=e;project=p;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getFoto_naam() {
		return this.foto_naam;
	}

	public void setFoto_naam(String foto_naam) {
		this.foto_naam = foto_naam;
	}

	public Experimenten getExperimenten() {
		return this.experimenten;
	}

	public void setExperimenten(Experimenten experimenten) {
		this.experimenten = experimenten;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}