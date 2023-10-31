
package org.onebeartoe.development.tools.sasquatch;

import java.util.List;
import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;

/**
 *
 */
public class StateCoordinatesServiceSpecification 
{
    StateCoordinatesService implementation;
    
    public StateCoordinatesServiceSpecification()
    {
        implementation = new StateCoordinatesService();
    
    }
    
    /**
     * The expected count of the state names was derived from line counting 
     * the states.csv file.
     */
    @Test
    public void stateNames()
    {
        int expected = 52;
        
        List<String> names = implementation.stateNames();
     
        int actual = names.size();
        
        assertEquals(expected, actual);
    }
}
