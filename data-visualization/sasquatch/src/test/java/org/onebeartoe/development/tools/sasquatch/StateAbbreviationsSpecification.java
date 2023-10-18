
package org.onebeartoe.development.tools.sasquatch;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;

/**
 *
 */
public class StateAbbreviationsSpecification
{
    StateAbbreviations implementation = new StateAbbreviations();
    
    /**
     * Verify the US state count is 50, as of 2023.
     */
    @Test
    public void count()
    {
        int actual = implementation.getCount();
        
        int expected = 50;
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void getAbbreviattion_pass()
    {   
        var state1 = "Texas";
        
        var expected = "TX";
        
        String actual = implementation.getAbbreviation(state1);
        
        assertEquals(expected, actual);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getAbbreviattion_fail()
    {
        var jibberishState = "Betoware";
        
        implementation.getAbbreviation(jibberishState);
    }
}
