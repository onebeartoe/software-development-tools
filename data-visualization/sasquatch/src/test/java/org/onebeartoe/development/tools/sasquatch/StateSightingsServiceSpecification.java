
package org.onebeartoe.development.tools.sasquatch;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;

/**
 *
 */
public class StateSightingsServiceSpecification
{
    StateSightingsService implementation = new StateSightingsService();
    
    /**
     * Assert the correct sighting count, latitude and longitude are returned.
     * 
     * The expected values are looked up in SasquatchSightingsServiceSpecification.
     * 
     * @param state 
     */
    @Test
    public void sightingsFor_pass()
    {
        String state = "Texas";
        
        SasquatchSighting sighting = implementation.sightingsFor(state);

        long expectedCount = 231;
        long actualCount = sighting.count;
        assertEquals(expectedCount, actualCount);

        String expectedState = state;
        String actualState = sighting.state;
        assertEquals(expectedState, actualState);

        String expectedLatitude = "31.968599";
        float actualLatitude = sighting.latitude;
        assertEquals(expectedLatitude, actualLatitude);

        String expectedLongitude = "-99.901813";
        float actualLongitude = sighting.longitude;
        assertEquals(expectedLongitude, actualLongitude);
    }

    /**
     * Pass an jibberish U.S. State name and expect and exception.
     * 
     * @param state 
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void sightingsFor_fail()
    {
        implementation.sightingsFor("San Antonio");
    }
    
    /**
     * I pulled these begin and end dates directly from the Excel file,
     * using the sort filter. 
     * 
     * The end date expected is teh calendar-wise last date of the entries
     */
    @Test
    public void endDate()
    {
        String expected = "2023-02-28";
        
        String actual = implementation.getEndDate();
        
        assertEquals(expected, actual);
    }
    
    /**
     * I pulled these begin and end dates directly from the Excel file,
     * using the sort filter. 
     * 
     * I am not sure about that 1869 entry though.
     * 
     * The end date expected is teh calendar-wise first date in the entries.
     */
    @Test
    public void startDate()
    {
        
        
//TODO: which is the correct begin date        
//        1869-11-10?
//        1921-01-14
        String expected = "1869-11-10";
        
        String actual = implementation.getStartDate();
        
        assertEquals(expected, actual);
    }    
}
