/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.CommentEvenement;
import entities.Evenement;
import entities.User;
import java.sql.SQLDataException;
import java.util.List;

/**
 *
 * @author dell
 */
public interface CommentServiceInterface {
    
    public void addComment(CommentEvenement c)throws SQLDataException;
    public void modifieComment(CommentEvenement c)throws SQLDataException;
    public void deleteComment(int idComment)throws SQLDataException;
    public Evenement findEvenementById(int id);
    public User findUserById(int id);
    public CommentEvenement finCommentById(int id);

    public List<CommentEvenement> getAllCommentByEvent(Evenement e )throws SQLDataException;
}
