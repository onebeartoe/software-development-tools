
package org.onebeartoe.development.tools.sasquatch;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStream;
import java.util.List;

/**
 *
 */
public class StateSightingsService
{
    private final StateCoordinatesService coordinatesService;
    
    private final SasquatchSightingsService sightingsService;
            
    public StateSightingsService()
    {
        coordinatesService = new StateCoordinatesService();
        
        sightingsService = new SasquatchSightingsService();
    }

    public SasquatchSighting sightingsFor(String state)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }    

    public String getEndDate()
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getStartDate()
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
