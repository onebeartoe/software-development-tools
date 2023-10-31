
package org.onebeartoe.development.tools.sasquatch;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This service provides sighting counts for U.S. States.
 */
public class SasquatchSightingsService
{
    final static String sightingsCsvPath = "bfro_reports_geocoded.csv";
    
    private String startDate;
    
    private String endDate;
    
    private int sightingsCount;

    private final Map<String, Long> stateSightingsCountMap;
    
    private int parseExceptionCount;
    
    public SasquatchSightingsService()
    {
        InputStream inStream = ClassLoader.getSystemResourceAsStream(sightingsCsvPath);
        
        InputStreamReader reader = new InputStreamReader(inStream);
        
        List<SasquatchSighting> beans = new CsvToBeanBuilder(reader)
                .withType(SasquatchSighting.class)
                .build()
                .parse();
        
        sightingsCount = beans.size();
        
        var grouping = Collectors.groupingBy(SasquatchSighting::getState, Collectors.counting());
        
        stateSightingsCountMap = beans.stream()
                                      .collect(grouping);        
        
        parseDateInfo(beans);
        
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

    public String getStartDate() 
    {
        return new String(startDate);
    }

    private void parseDateInfo(List<SasquatchSighting> beans)
    {
        String earliest = null;
        
        String latest = null;
        
        // some date way in the future
        LocalDate earliestDate = LocalDate.parse("2518-05-05");
        
        // some date way in the past
        LocalDate latestDate = LocalDate.parse("1997-05-05");
        
        for(SasquatchSighting sighting : beans)
        {
            try
            {
                LocalDate currentDate = LocalDate.parse(sighting.date);

                if(currentDate.isBefore(earliestDate))
                {
                    earliestDate = currentDate;

                    earliest = sighting.date;
                }

                if(currentDate.isAfter(latestDate))
                {
                    latestDate = currentDate;

                    latest = sighting.date;
                }
            }
            catch(DateTimeParseException exception)
            {
                parseExceptionCount++;
            }
        }
        
        // maybe just used the instance varabies in the loop above, instead of 
        // setting here
        startDate = earliest;
        
        endDate = latest;
    }

    public int getSightingsCount() 
    {
        return sightingsCount;
    }

    public int unparsableSightingDates() 
    {
        return parseExceptionCount;
    }

    public String getEndDate() 
    {
        return endDate;
    }
}
