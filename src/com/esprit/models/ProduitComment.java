/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.models;

import java.util.Objects;

/**
 *
 * @author Ahmed
 */
public class ProduitComment {

       private int id;
    private int user_id;
    private String content;
    private int produitPending_id;
    private String username;
    
    
    public ProduitComment() {
    }

    public ProduitComment(String content) {
        this.content = content;
    }

    public ProduitComment(String content, String username) {
        this.content = content;
        this.username = username;
    }

    public ProduitComment(int user_id, String content, int produitPending_id) {
        this.user_id = user_id;
        this.content = content;
        this.produitPending_id = produitPending_id;
    }

    public ProduitComment(int id, int user_id, String content, int produitPending_id) {
        this.id = id;
        this.user_id = user_id;
        this.content = content;
        this.produitPending_id = produitPending_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduitPending_id() {
        return produitPending_id;
    }

    public void setProduitPending_id(int produitPending_id) {
        this.produitPending_id = produitPending_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return  "publié par "+username+" : " + content ;
    }

   

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.id;
        hash = 41 * hash + this.user_id;
        hash = 41 * hash + Objects.hashCode(this.content);
        hash = 41 * hash + this.produitPending_id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProduitComment other = (ProduitComment) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.user_id != other.user_id) {
            return false;
        }
        if (this.produitPending_id != other.produitPending_id) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        return true;
    }

    
  
}
