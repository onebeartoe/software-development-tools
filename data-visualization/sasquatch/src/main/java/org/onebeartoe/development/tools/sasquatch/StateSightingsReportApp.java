
package org.onebeartoe.development.tools.sasquatch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * This application outputs a CSV file with the 
 * State name, sighting count, longitude, and latitude.
 * 
 * The trailer file contains the start and end dates of the sightings,
 * as well as any parsing error details.
 */
public class StateSightingsReportApp
{
    private final StateSightingsService sightingsService = new StateSightingsService();
    
    public static void main(String[] args) throws IOException
    {
        StateSightingsReportApp app = new StateSightingsReportApp();
        
        String outpath = "target/report.text";
        
        String report = app.generateReport(outpath);
        
        Path path = Path.of(outpath);
        
        Files.writeString(path, report);
    }

    private String generateReport(String outpath)
    {
        List<String> stateNames = sightingsService.stateNames();
        
        StringBuffer report = new StringBuffer();
        
        String fieldSeparator = ",";
                
        // header
        String header = "state,count,latitude,longitude";
        report.append(header);
        report.append(System.lineSeparator());
        
        // indiviaual records
        for(String name : stateNames)
        {
            try
            {
                SasquatchSighting sighting = sightingsService.sightingsFor(name);

                report.append(sighting.state + fieldSeparator +
                        sighting.count + fieldSeparator +
                        sighting.latitude + fieldSeparator +
                        sighting.longitude + fieldSeparator 
                        );
                
                report.append(System.lineSeparator());
            }
            catch(IllegalArgumentException excepton)
            {
                excepton.printStackTrace();
            }
        }
        
        // footer
        report.append("start date: " +
                sightingsService.getStartDate() +

                "\tend date: " + 
                sightingsService.getEndDate() + 

                "\ttotal sightings: " + 
                sightingsService.getSightingsCount() + 

                "\tunparsable sightings: " +
                sightingsService.unparsableSightingDates()
                );
        
        return report.toString();
    }
}
