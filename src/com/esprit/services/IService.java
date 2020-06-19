package com.esprit.services;

import com.esprit.models.Produit;
import java.util.List;

public interface IService<T> {

    public void ajouter(T t);

    public void supprimer(String id);

    public void modifier(T t);

}
