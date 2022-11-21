
package org.onebeartoe.development.tools.titanic.statistics;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.List;
import org.onebeartoe.development.tools.titanic.TitanicDataSource;

/**
 *
 */
public class ImportTitanicCsvToDatabase 
{
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException 
    {
        ImportTitanicCsvToDatabase app = new ImportTitanicCsvToDatabase();
        
        List<TitanicPassenger> passengers = app.parseCsvFile();
        
        app.populateDatabase(passengers);
    }

    private void populateDatabase(List<TitanicPassenger> passengers) throws SQLException 
    {
//        Connection conn = TitanicDataSource.getConnection();
  TitanicDataSource.persistPassengers(passengers);
        
    
//    String dbms = "derby";

    
//    String dbName = "derby" ;
    //String dbName = "C:\\home\\owner\\betoland-subversion\\School\\New-Apprenticeship\\tableau-assignments\\titanic-passenengers\\db\\derby" ;
     
    

 
// works!
//String url = "jdbc:derby:demo;create=true";

        //String url = "jdbc:derby:db;";

 //       String url = "jdbc:derby:demo;";

      //  Connection conn = DriverManager.getConnection(url);
        
    //    System.out.println("Connected to database");

  //      Statement statement = conn.createStatement();

//        ResultSet rs = statement.executeQuery("SELECT * FROM app.TITANIC_PASSENGER_LIST_CSV ORDER BY NAME");    
                
        System.out.println("End of run.");        
    }

    private List<TitanicPassenger> parseCsvFile() throws FileNotFoundException 
    {        
        System.out.println("Hello World!");
    
        String fileName = "C:\\home\\owner\\betoland-subversion\\School\\New-Apprenticeship\\tableau-assignments\\titanic-passenengers\\titanic-passenger-list.csv";

        List<TitanicPassenger> beans = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(TitanicPassenger.class)
                .build()
                .parse();

        beans.forEach(p -> {
//            System.out.println(p.name + " - " + p.homeDest);
                });
        
        return beans;
    }
}
