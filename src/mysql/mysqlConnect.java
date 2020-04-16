package mysql;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class mysqlConnect {
    private final String URL = "jdbc:mysql://localhost:3306/symfony";
    private final String PWD = "";
    private final String Login = "root";
    private Connection cnx;
    private static mysqlConnect instance;
    
    private mysqlConnect() {
        try {
            cnx = DriverManager.getConnection(URL, Login, PWD);
            System.out.println("Connecté à la Base de données !");
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static mysqlConnect getInstance() {
        if(instance == null)
            instance = new mysqlConnect();
        return instance;
    }
    
    public Connection getCnx() {
        return cnx;
    }
    
    
}
