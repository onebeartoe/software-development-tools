
package org.onebeartoe.development.tools.titanic.statistics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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


        

//        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

        
        Connection conn = null;
 
//        Class.forName("org.apache.derby.jdbc.ClientDriver");

        //pwd()
        
//      DriverManager.registerDriver(new ClientDriver());
//DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());

        
//    Properties connectionProps = new Properties();
//    connectionProps.put("user", this.userName);
//    connectionProps.put("password", this.password);

//String driver = "org.apache.derby.jdbc.EmbeddedDriver";
//Class.forName(driver).newInstance();

//String url = "jdbc:derby:memory:demo;create=true";

// works!
//String url = "jdbc:derby:demo;create=true";

String url = "jdbc:derby:demo;";
//String url = "jdbc:derby:db;";

//String url = "jdbc:derby:db:app;";
//String url = "jdbc:derby:db:app;create=true";
//String url = "jdbc:" + dbms + ":" + dbName + ":app" + ";";

        conn = DriverManager.getConnection(url);
//                           +
//                   ";create=true",
//                   connectionProps);
        
            System.out.println("Connected to database");

    }
}
