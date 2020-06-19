/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.models;

/**
 *
 * @author lenovo
 */
public class CommentairePub {
    private int id;
    private int publication_id;
    private String description ;

    public CommentairePub() {
    }

    public CommentairePub(int id, int publication_id, String description) {
        this.id = id;
        this.publication_id = publication_id;
        this.description = description;
    }

    public CommentairePub(int publication_id, String description) {
        this.publication_id = publication_id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getPublication_id() {
        return publication_id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPublication_id(int publication_id) {
        this.publication_id = publication_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "publication =" + publication_id + ", description=" + description ;
    }
    
    
}
