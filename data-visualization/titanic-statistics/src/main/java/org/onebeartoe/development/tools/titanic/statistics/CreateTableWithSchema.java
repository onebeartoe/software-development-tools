
package org.onebeartoe.development.tools.titanic.statistics;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import org.onebeartoe.development.tools.titanic.TitanicDataSource;

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
        TitanicDataSource.createDatabaseTable();
                
        System.out.println("End of run.");        
    }
}















        