
package org.onebeartoe.development.tools.sasquatch;

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
        SasquatchSighting sighting = new SasquatchSighting();
        
        sighting.count = sightingsService.sightingsFor(state);
        
        sighting.state = state;
        
        StateCoordinates coordinates = coordinatesService.coordinatesFor(state);
        
        sighting.latitude = coordinates.latitude;
        
        sighting.longitude = coordinates.longitude;
        
        return sighting;
    }    

    public String getEndDate()
    {
        return sightingsService.getEndDate();
    }

    public String getStartDate()
    {
        String startDate = sightingsService.getStartDate();
        
        return startDate;
    }

    public int getSightingsCount() 
    {
        return sightingsService.getSightingsCount();
    }

    public int unparsableSightingDates()
    {
        return sightingsService.unparsableSightingDates();
    }

    public List<String> stateNames()
    {
        return coordinatesService.stateNames();
    }
}
