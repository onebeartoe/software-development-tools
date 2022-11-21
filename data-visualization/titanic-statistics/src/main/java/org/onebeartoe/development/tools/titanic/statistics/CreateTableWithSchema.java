
package org.onebeartoe.development.tools.titanic.statistics;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 */
public class CreateTableWithSchema 
{
    private TitanicDataSource dataSource;

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException 
    {
        CreateTableWithSchema app = new CreateTableWithSchema();
                        
        app.createDatabaseTable();
        
        
    }

    private void createDatabaseTable() throws SQLException 
    { 
        dataSource.createDatabaseTable();
                
        System.out.println("End of run.");        
    }
}















        