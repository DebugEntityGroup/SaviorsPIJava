/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.tests;

import com.esprit.services.ServiceCategorieProduit;
import com.esprit.services.ServiceProduit;
import com.esprit.services.ServiceProduitComment;
import java.sql.SQLException;

/**
 *
 * @author Ahmed
 */
public class MainProg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {

        ServiceProduit p = new ServiceProduit();
        ServiceCategorieProduit c1 = new ServiceCategorieProduit();
        ServiceProduitComment m = new ServiceProduitComment();
        //c1.ajouter(new CategorieProduit("informatique", "hot gamme"));
        //p.ajouter(new Produit(19,"pc hp", "imagev", 560, "good produit","informatique"));
        //p.modifier(new Produit(1 ,19, "mercedes", "image", 999970, "woow produit","voiture"));

        //m.ajouter(new ProduitComment(19,"gooooood job!!!!!!",1));
        //m.modifier(new ProduitComment(5 ,19, "Baaad produit -_______-",1));
        //m.supprimer(new ProduitComment(5 ,19, "Baaad produit -_______-",1));
       // p.afficher().forEach(System.out::println);
        //p.TriAscendant().forEach(System.out::println);
        //p.TriDescendant().forEach(System.out::println);
        //System.out.println(p.SumPrix("voiture"));
        //System.out.println(p.AfficherDetailProduit(5));
       p.produitsParMC("mercedes").forEach(System.out::println);
       // System.out.println(p.seulProduit(3));
    }

}
