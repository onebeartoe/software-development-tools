
package org.onebeartoe.development.tools.sasquatch;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author roberto
 */
public class SasquatchSightingParsing
{
    public static void main(String[] args) throws FileNotFoundException 
    {
        System.out.println("Hello World!");
        
        SasquatchSightingParsing app = new SasquatchSightingParsing();
        
        List<SasquatchSighting> sightings = app.parseCsvFile();
        
        app.pairStateWithCoordinates(sightings);
        
        app.outputJavascriptValues();
    }
    
    private List<SasquatchSighting> parseCsvFile() throws FileNotFoundException 
    {        
        System.out.println("Hello World!");
    
        String fileName = "src/main/resources/bfro_reports_geocoded.csv";
        
        List<SasquatchSighting> beans = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(SasquatchSighting.class)
                .build()
                .parse();
        
        Map<String, Long> stateSightingsCount = beans.stream()
                              .collect(Collectors.groupingBy(SasquatchSighting::getState, Collectors.counting()));
        
        Set<String> keySet = stateSightingsCount.keySet();
        
        for(String key : keySet)
        {
            System.out.println(key + ": " + stateSightingsCount.get(key));
        }        
        
        return beans;
    }    

    private void pairStateWithCoordinates(List<SasquatchSighting> sightings) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void outputJavascriptValues() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
