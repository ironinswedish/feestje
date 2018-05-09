package com.example.user.vibrapp;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the personen database table.
 * 
 */
public class Personen implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idpersonen;

	private String email;

	private String naam;

	private String rol;

	private String school;

	private String wachtwoord;

	private List<Project> projects;

	public Personen() {
	}
	
	public Personen(String n,String w,String e,String s,String r) {
		naam=n; wachtwoord=w; email=e; school=s;rol=r;
	}

	public int getIdpersonen() {
		return this.idpersonen;
	}

	public void setIdpersonen(int idpersonen) {
		this.idpersonen = idpersonen;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getRol() {
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getWachtwoord() {
		return this.wachtwoord;
	}

	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		project.setPersonen(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setPersonen(null);

		return project;
	}

}