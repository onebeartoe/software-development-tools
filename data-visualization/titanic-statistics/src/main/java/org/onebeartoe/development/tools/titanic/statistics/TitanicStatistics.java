
package org.onebeartoe.development.tools.titanic.statistics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */
public class TitanicStatistics 
{
    public static
    String dbms = "derby";

    public static
    String dbName = "derby" ;
    //String dbName = "C:\\home\\owner\\betoland-subversion\\School\\New-Apprenticeship\\tableau-assignments\\titanic-passenengers\\db\\derby" ;
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException 
    {
        System.out.println("Hello World!");
 
// works!
//String url = "jdbc:derby:demo;create=true";

        String url = "jdbc:derby:db;";

 //       String url = "jdbc:derby:demo;";

        Connection conn = DriverManager.getConnection(url);
        
        System.out.println("Connected to database");

        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM app.TITANIC_PASSENGER_LIST_CSV ORDER BY NAME");
    }
}
