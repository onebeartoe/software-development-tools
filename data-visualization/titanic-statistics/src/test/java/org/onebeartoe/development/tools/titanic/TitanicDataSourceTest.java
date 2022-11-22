
package org.onebeartoe.development.tools.titanic;

import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.onebeartoe.development.tools.titanic.statistics.TitanicPassenger;

/**
 *
 */
public class TitanicDataSourceTest 
{
    @Test
    public void creationAndRetievalTest() throws SQLException
    {
        List<TitanicPassenger> passengers = TitanicDataSource.retrievePassengers();
        
        int actual = passengers.size();
        
        int expected = 1309;
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void casualtiesReportTest() throws SQLException
    {
        CasualtyReport report = TitanicDataSource.casualtiesReport();
        
        assertTrue(report.all.survived == 500);
        
        assertTrue(report.all.perished == 809);
    }
}
