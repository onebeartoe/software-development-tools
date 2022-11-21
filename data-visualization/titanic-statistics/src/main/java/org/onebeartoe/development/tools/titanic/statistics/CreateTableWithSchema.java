
package org.onebeartoe.development.tools.titanic.statistics;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 */
public class CreateTableWithSchema 
{
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException 
    {
        CreateTableWithSchema app = new CreateTableWithSchema();
                        
        app.createDatabaseTable();
    }

    private void createDatabaseTable() throws SQLException 
    {
    
  //  String dbms = "derby";

    
//    String dbName = "derby" ;
    //String dbName = "C:\\home\\owner\\betoland-subversion\\School\\New-Apprenticeship\\tableau-assignments\\titanic-passenengers\\db\\derby" ;
     
    

 
// works!
String url = "jdbc:derby:derby;create=true";

//        String url = "jdbc:derby:db;";

 //       String url = "jdbc:derby:demo;";

        Connection conn = DriverManager.getConnection(url);
        
        System.out.println("Connected to database");

        Statement statement = conn.createStatement();

//        boolean execute = statement.execute("CREATE TABLE TITANIC_PASSENGER_LIST_CSV (name varchar(255))");
//        System.out.println("execute = " + execute);
        
//        String select = "SELECT * FROM app.TITANIC_PASSENGER_LIST_CSV ORDER BY NAME";        
        String select = "SELECT * FROM TITANIC_PASSENGER_LIST_CSV ORDER BY NAME";
        
        ResultSet rs = statement.executeQuery(select);    
                
        System.out.println("End of run.");        
    }

    
}
