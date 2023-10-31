
package org.onebeartoe.development.tools.sasquatch;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;

/**
 *
 */
public class SasquatchSightingsServiceSpecification
{
    SasquatchSightingsService implementation = new SasquatchSightingsService();
    
    @Test
    public void sightingsFor_pass()
    {
        String inpath = SasquatchSightingsService.sightingsCsvPath;

        InputStream inStream = ClassLoader.getSystemResourceAsStream(inpath);
        
        InputStreamReader reader = new InputStreamReader(inStream);
        
        List<SasquatchSighting> beans = new CsvToBeanBuilder(reader)
                .withType(SasquatchSighting.class)
                .build()
                .parse();
        
        
        String state = "Texas";
        
        int expectedCount = 0;
        
        for(SasquatchSighting s : beans)
        {
            if( s.getState().equals(state) )
            {
                expectedCount++;
            }
        }
        
        long actualCount = implementation.sightingsFor(state);
        
        assertEquals(expectedCount, actualCount);
    }
    
    /**
     * Pass an jibberish U.S. State name and expect and exception.
     * 
     * @param state 
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void sightingsFor_fail()
    {
        long sightingsFor = implementation.sightingsFor("Dallas");
        
        System.out.println("sightingsFor = " + sightingsFor);
    }
}
