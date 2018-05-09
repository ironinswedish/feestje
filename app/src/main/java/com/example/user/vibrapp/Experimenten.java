package com.example.user.vibrapp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the experimenten database table.
 */
public class Experimenten implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private Date date;

    private String titel;

    private Project project;

    private List<Foto> fotos;

    private List<Meting> metings;

    public int getIdMeting() {
        return idMeting;
    }

    private int idMeting;

    public Experimenten() {
    }

    public Experimenten(String csv) {
        String cvsSplitBy = ",";


        String[] p = csv.split(cvsSplitBy);
        id = Integer.parseInt(p[0]);
        titel = p[1];
        date = new Date(Long.parseLong(p[2]));
        if (p[3].equals("null")){
            idMeting = -1;
        }else{
            idMeting = Integer.parseInt(p[3]);
        }

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitel() {
        return this.titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Foto> getFotos() {
        return this.fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public Foto addFoto(Foto foto) {
        getFotos().add(foto);
        foto.setExperimenten(this);

        return foto;
    }

    public Foto removeFoto(Foto foto) {
        getFotos().remove(foto);
        foto.setExperimenten(null);

        return foto;
    }

    public List<Meting> getMetings() {
        return this.metings;
    }

    public void setMetings(List<Meting> metings) {
        this.metings = metings;
    }

    public Meting addMeting(Meting meting) {
        getMetings().add(meting);
        meting.setExperimenten(this);

        return meting;
    }

    public Meting removeMeting(Meting meting) {
        getMetings().remove(meting);
        meting.setExperimenten(null);

        return meting;
    }

}