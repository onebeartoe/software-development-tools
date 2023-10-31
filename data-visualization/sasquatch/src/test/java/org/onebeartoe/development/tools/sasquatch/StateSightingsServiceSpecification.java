
package org.onebeartoe.development.tools.sasquatch;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
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
        String actualLatitude = sighting.latitude;
        assertEquals(expectedLatitude, actualLatitude);

        String expectedLongitude = "-99.901813";
        String actualLongitude = sighting.longitude;
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
    
    /**
     * This test ensures the correct number of records are read from the input file.
     * 
     * The expected value was derived by line counting the input file - 1 for the header.
     */
    @Test
    public void sightingsCount()
    {
        int expected = 5082;

        int actual = implementation.getSightingsCount();
        
        assertEquals(expected, actual);
    }
    
    /**
     * This tests ensures that sighting date count is available 
     * and has reasonable values.
     * 
     * There are some blank/weird entries so we know there are some un-parsable
     * date values.
     */
    @Test
    public void unparsableSightingDates()
    {        
        int count = implementation.unparsableSightingDates();
        
        assertTrue(count > 1);
    }
}
