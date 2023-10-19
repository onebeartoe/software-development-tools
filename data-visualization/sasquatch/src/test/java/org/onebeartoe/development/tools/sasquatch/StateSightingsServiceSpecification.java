
package org.onebeartoe.development.tools.sasquatch;

import org.testng.annotations.Test;

/**
 *
 */
public class StateSightingsServiceSpecification
{
    StateSightingsService implementation = new StateSightingsService();
    
    /**
     * Assert the correct sighting count, latitude and longitude are returned
     * @param state 
     */
    @Test
    public void sightingsFor_pass(String state)
    {
        SasquatchSighting sighting = implementation.sightingsFor("Texas");

//TODO:
int i = 2 /0;
//TODO:        
//What is hte count?        
//        TX	31.968599	-99.901813	Texas
//assert the values in line above                
    }

    /**
     * Pass an jibberish U.S. State name and expect and exception.
     * 
     * @param state 
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void sightingsFor_fail(String state)
    {
        implementation.sightingsFor("San Antonio");
    }
    
    /**
     * I pulled these begin and end dates directly from the Excel file,
     * using the sort filter. 
     * 
     * I am not sure about that 1869 entry though.
     */
    @Test
    public void dates()
    {
//TODO: which is the correct begin date        
//        1869-11-10?
//        1921-01-14
//TODO: assert teh end date
//        2023-02-28
int date = 2/ 0;

    }
}
