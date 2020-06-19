/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.CommentEvenement;
import entities.Evenement;
import entities.Participer;
import entities.User;
import java.sql.Date;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.CommentService;
import service.EvenementService;

/**
 *
 * @author dell
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLDataException {
        
                DataSource cnx=  DataSource.getInstance();
                
                Date d1 = new Date(01,01,2020);
                Date d2 = new Date(01,01,2020);
                
                User U = new User(18,"folan");
              
                //Evenement e = new Evenement(0, 0, "racharacha", "hjjkk", "image", "shjk", "lieu", d2);
                //Evenement e1 = new Evenement(11, 5, 5, "racharacha", "hahaha", "bbbb", "brbrbr", "hdhd", d2);
                List<Evenement> L = new ArrayList<Evenement>();
                /*CommentEvenement c = new CommentEvenement(e1, U, " good job ");
                CommentEvenement c1 = new CommentEvenement(e1, U, 17, "Cahngenement");*/


                //System.out.println("utils.Test.main()"+e);
                List<CommentEvenement> Le = new ArrayList<CommentEvenement>();
                List<CommentEvenement> Lc = new ArrayList<CommentEvenement>();
                EvenementService sv = new EvenementService();
                
                //sv.addEvenement(e);
                // sv.ModifierEvenenment(e1);
                //sv.deleteEvenement(10);
                /*
                L = sv.getAllEvenement();
                for (Evenement event : L)
                {
                System.out.println(event);
                }
                */
                /*
                L = sv.afficheEvenement("hhhh");
                for (Evenement event : L)
                {
                System.out.println(event);
                }
                */
               // sv.ModifierEvenenmentPlace(e1);
                
  //////////////////////////////////////////////////////////////////////////////////////////////////////              
                
                
                CommentService sc = new CommentService();
                
               // sc.addComment(c);
               //sc.deleteComment(4);
              // sc.modifieComment(c1);
               
              
           
    }
}
