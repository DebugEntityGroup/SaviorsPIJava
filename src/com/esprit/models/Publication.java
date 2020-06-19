/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.models;

import static jdk.nashorn.internal.runtime.Debug.id;

/**
 *
 * @author lenovo
 */
public class Publication {
    private int id;
    private String titre;
    private String description;
    private String brochure_filename;
    private String video;

    public Publication() {
    }

    public Publication(int id, String titre, String description, String brochure_filename, String video) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.brochure_filename = brochure_filename;
        this.video = video;
    }

    public Publication(String titre, String description, String brochure_filename, String video) {
        this.titre = titre;
        this.description = description;
        this.brochure_filename = brochure_filename;
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getBrochure_filename() {
        return brochure_filename;
    }

    public String getVideo() {
        return video;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBrochure_filename(String brochure_filename) {
        this.brochure_filename = brochure_filename;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "publication{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", brochure_filename=" + brochure_filename + ", video=" + video + '}';
    }

   
}

