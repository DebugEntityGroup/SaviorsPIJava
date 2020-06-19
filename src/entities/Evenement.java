
package entities;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author dell
 */
public class Evenement {
    private int id ,nbrInterest ,moyenne;
    private String name ,description , image , lieu ;
    private Date Dateevent  ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbrInterest() {
        return nbrInterest;
    }

    public void setNbrInterest(int nbrInterest) {
        this.nbrInterest = nbrInterest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Date getDateevent() {
        return Dateevent;
    }

    public void setDateevent(Date Dateevent) {
        this.Dateevent = Dateevent;
    }

    public int getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(int moyenne) {
        this.moyenne = moyenne;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", nbrInterest=" + nbrInterest + ", moyenne=" + moyenne + ", name=" + name + ", description=" + description + ", image=" + image + ", lieu=" + lieu + ", Dateevent=" + Dateevent + '}';
    }

    public Evenement(int nbrInterest, int moyenne, String name, String description, String image, String lieu, Date Dateevent) {
        this.nbrInterest = nbrInterest;
        this.moyenne = moyenne;
        this.name = name;
        this.description = description;
        this.image = image;
        this.lieu = lieu;
        this.Dateevent = Dateevent;
    }

    public Evenement( int id ,String name, String description, String lieu, Date Dateevent) {
        this.id = id ;
        this.name = name;
        this.description = description;
        this.lieu = lieu;
        this.Dateevent = Dateevent;
    }
    
    

    public Evenement() {
    }

    public Evenement(int id, int nbrInterest, int moyenne, String name, String description, String image, String lieu, Date Dateevent) {
        this.id = id;
        this.nbrInterest = nbrInterest;
        this.moyenne = moyenne;
        this.name = name;
        this.description = description;
        this.image = image;
        this.lieu = lieu;
        this.Dateevent = Dateevent;
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.id;
        hash = 41 * hash + this.nbrInterest;
        hash = 41 * hash + this.moyenne;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.image);
        hash = 41 * hash + Objects.hashCode(this.lieu);
        hash = 41 * hash + Objects.hashCode(this.Dateevent);
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
        final Evenement other = (Evenement) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.nbrInterest != other.nbrInterest) {
            return false;
        }
        if (this.moyenne != other.moyenne) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.lieu, other.lieu)) {
            return false;
        }
        if (!Objects.equals(this.Dateevent, other.Dateevent)) {
            return false;
        }
        return true;
    }

   

    

   
   
    
   
    

 
}