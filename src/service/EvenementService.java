
package service;

import entities.Evenement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DataSource;

/**
 *
 * @author dell
 */
public class EvenementService implements EvenementServiceInterface{
    
    Connection cn=DataSource.getInstance().getCnx();
    
    @Override
    public void addEvenement(Evenement q)throws SQLDataException{
        
         
 String query ="INSERT INTO `eventPending`( `name`, `description`, `nbrInterest`,`Dateevent`,`image`,`Lieu`,`moyenne`) VALUES (?,?,?,?,?,?,?)";
 
         PreparedStatement st;
        
        try {
            st = cn.prepareStatement(query);
                st.setString(1,q.getName());
                st.setString(2,q.getDescription());
                st.setInt(3,q.getNbrInterest());
                st.setDate(4,q.getDateevent());
                st.setString(5,q.getImage());
                st.setString(6,q.getLieu());
                st.setInt(7,q.getMoyenne());
                st.executeUpdate();
                
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                

    }

    @Override
    public boolean ModifierEvenenment(Evenement e) throws SQLDataException {

               
        try {
            String query = "UPDATE `eventPending` SET `name`=?,`description`=?,`Dateevent`=?,`Lieu`=? WHERE `id` = ?";
            
            
            PreparedStatement st= cn.prepareStatement(query) ;
            
            st.setString(1,e.getName());
            st.setString(2,e.getDescription());
           
            st.setDate(3,e.getDateevent());
            st.setString(4,e.getLieu());
            st.setInt(5,e.getId());
            st.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        return false ;
        }
        
        
    }

    @Override
    public boolean deleteEvenement(int idEvenement) throws SQLDataException {
 
        
        try {
            
            Statement st=cn.createStatement();
            String req= "DELETE FROM `eventPending` WHERE `id` ="+idEvenement;
            st.executeUpdate(req);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }    
    
    }

    @Override
    public List<Evenement> getAllEvenement() throws SQLDataException {
        List<Evenement> list =new ArrayList<Evenement>();
        int count =0;
        
        String requete="select * from eventPending";
         try{
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(requete);
            
            while (rs.next()){
                
                Evenement e = new Evenement();
                e.setId(rs.getInt(1));
                e.setName(rs.getString(2));
                e.setDescription(rs.getString(3));
                e.setNbrInterest(rs.getInt(4));
                e.setLieu(rs.getString(6));
                e.setDateevent(rs.getDate(9));
                e.setImage(rs.getString(8));
                e.setMoyenne(rs.getInt(7));

                  
                
                list.add(e);
                
                count++;
            }
            if(count == 0){
                return null;
           }else{
             ObservableList lc_final = FXCollections.observableArrayList(list);

               return lc_final;
            
           
        }
         }
        catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
   
    }   

    @Override
    public List<Evenement> afficheEvenement(String Name) throws SQLDataException {
  List<Evenement> list=new ArrayList<Evenement>();
           try {
               String req="SELECT * FROM eventPending where name='"+Name+"'";
               Statement st;
                 st = cn.createStatement();
                 ResultSet rs=st.executeQuery(req);
               
                while(rs.next())
                       {
                 Evenement e = new Evenement();
                e.setId(rs.getInt(1));
                e.setName(rs.getString(2));
                e.setDescription(rs.getString(3));
                e.setNbrInterest(rs.getInt(4));
                e.setLieu(rs.getString(6));
                e.setDateevent(rs.getDate(9));
                e.setImage(rs.getString(8));
                e.setMoyenne(rs.getInt(7));
                list.add(e);

                       }    
           } catch (SQLException ex) {
               Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
           }
          return list;    }

    @Override
    public boolean ModifierEvenenmentPlace(Evenement e) throws SQLDataException {
        String query = "UPDATE eventPending SET nbrInterest=? WHERE id = ?";
		PreparedStatement st;
        try {
                st = cn.prepareStatement(query);
            
                st.setInt(1,e.getNbrInterest());
                st.setInt(2,e.getId());
                st.executeUpdate();
                return true;
                
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
                
 
               

    
    
    

