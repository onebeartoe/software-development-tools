
package org.onebeartoe.development.tools.titanic;

import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.onebeartoe.development.tools.titanic.statistics.TitanicPassenger;

/**
 *
 * @author roberto
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
        
        assertTrue(1 == 2 -1 );
        System.out.println("farto- d");
    }   
}
