/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Evenement;
import java.sql.SQLDataException;
import java.util.List;

/**
 *
 * @author dell
 */
public interface EvenementServiceInterface {
    
    public void addEvenement (Evenement e) throws SQLDataException;
    public boolean deleteEvenement(int idEvenement) throws SQLDataException;
    public boolean ModifierEvenenment (Evenement e) throws SQLDataException;
    public List <Evenement> getAllEvenement() throws SQLDataException;
    public List<Evenement> afficheEvenement(String titre) throws SQLDataException;
    public boolean ModifierEvenenmentPlace (Evenement e) throws SQLDataException;

}
