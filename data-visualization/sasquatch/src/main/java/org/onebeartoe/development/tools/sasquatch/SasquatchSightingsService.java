
package org.onebeartoe.development.tools.sasquatch;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This service provides sighting counts for U.S. States.
 */
public class SasquatchSightingsService
{
    final static String sightingsCsvPath = "bfro_reports_geocoded.csv";

    private final Map<String, Long> stateSightingsCountMap;
    
    public SasquatchSightingsService()
    {
        InputStream inStream = ClassLoader.getSystemResourceAsStream(sightingsCsvPath);
        
        InputStreamReader reader = new InputStreamReader(inStream);
        
        List<SasquatchSighting> beans = new CsvToBeanBuilder(reader)
                .withType(SasquatchSighting.class)
                .build()
                .parse();
        
        var grouping = Collectors.groupingBy(SasquatchSighting::getState, Collectors.counting());
        
        stateSightingsCountMap = beans.stream()
                                      .collect(grouping);        
        
        int size = stateSightingsCountMap.size();
        
        System.out.println("size = " + size);
    }
    
    public long sightingsFor(String state)
    {
        Long count = stateSightingsCountMap.get(state);
        
        if(count == null)
        {
            throw new IllegalArgumentException(state + ": is not recognized");
        }
        
        return count;
    }
}
