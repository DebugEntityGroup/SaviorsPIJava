/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.CommentEvenement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ListCell;

/**
 *
 * @author dell
 */
public class ListViewCommentEvent extends ListCell<CommentEvenement>{
    
 
        public void updateItem(CommentEvenement com,  boolean empty)
    {
      
        super.updateItem(com,empty);
        if(com != null)
        {
            
           
            CommentItemController data = new CommentItemController();
            try {
                data.setInfo(com);
            } catch (SQLException ex) {
                Logger.getLogger(ListViewCommentEvent.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                data.setInfo(com);
            } catch (SQLException ex) {
                Logger.getLogger(ListViewCommentEvent.class.getName()).log(Level.SEVERE, null, ex);
            }
            setGraphic(data.getBox());
        }
    }
    
    
}
