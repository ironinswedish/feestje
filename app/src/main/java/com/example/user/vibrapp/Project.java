package com.example.user.vibrapp;

import java.io.BufferedReader;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the project database table.
 */
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private String projectToken;

    private String projectType;

    private boolean public_;

    private String titel;

    private List<Experimenten> experimentens;

    private List<Foto> fotos;

    private Personen personen;
    private int idpersonen;

    private Locaty locaty;

    public Project() {
    }

    public Project(String csv) {
        String cvsSplitBy = ",";


        String[] p = csv.split(cvsSplitBy);
        id= Integer.parseInt(p[0]);
        titel= p[1];
        projectToken= p[2];
        idpersonen= Integer.parseInt(p[3]);
        public_= p[4]=="true";

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectToken() {
        return this.projectToken;
    }

    public void setProjectToken(String projectToken) {
        this.projectToken = projectToken;
    }

    public String getProjectType() {
        return this.projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public boolean getPublic_() {
        return this.public_;
    }

    public void setPublic_(boolean public_) {
        this.public_ = public_;
    }

    public String getTitel() {
        return this.titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public List<Experimenten> getExperimentens() {
        return this.experimentens;
    }

    public void setExperimentens(List<Experimenten> experimentens) {
        this.experimentens = experimentens;
    }

    public Experimenten addExperimenten(Experimenten experimenten) {
        getExperimentens().add(experimenten);
        experimenten.setProject(this);

        return experimenten;
    }

    public Experimenten removeExperimenten(Experimenten experimenten) {
        getExperimentens().remove(experimenten);
        experimenten.setProject(null);

        return experimenten;
    }

    public List<Foto> getFotos() {
        return this.fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public Foto addFoto(Foto foto) {
        getFotos().add(foto);
        foto.setProject(this);

        return foto;
    }

    public Foto removeFoto(Foto foto) {
        getFotos().remove(foto);
        foto.setProject(null);

        return foto;
    }

    public int getFirstFoto() {
        if (fotos.size() == 0) {
            return 16;
        } else {
            return fotos.get(0).getId();
        }

    }

    public Personen getPersonen() {
        return this.personen;
    }

    public void setPersonen(Personen personen) {
        this.personen = personen;
    }

    public Locaty getLocaty() {
        return this.locaty;
    }

    public void setLocaty(Locaty locaty) {
        this.locaty = locaty;
    }

}