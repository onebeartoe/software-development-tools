
package org.onebeartoe.development.tools.sasquatch;
        
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class StateCoordinatesService
{
    private final List<StateCoordinates> stateCoordinates;
    
    public StateCoordinatesService()
    {
        String name = "states.csv";
        
        InputStream inStream = ClassLoader.getSystemResourceAsStream(name);        

        InputStreamReader reader = new InputStreamReader(inStream);
        
        stateCoordinates = new CsvToBeanBuilder(reader)
            .withType(StateCoordinates.class)
            .withSeparator('\t')
            .build()
            .parse();
        
        System.out.println("stateCoordinates = " + stateCoordinates);
    }
    
    public StateCoordinates coordinatesFor(String stateName)
    {
        List<StateCoordinates> coordinatesList = stateCoordinates.stream()
                .filter(sc -> sc.name.equals(stateName))
                .collect( Collectors.toList());
        
        if(coordinatesList.size() > 1)
        {
            String message = "there is a collision on state names, " + stateName + ", while they should be unique";

            throw new IllegalStateException(message);
        }
        
        if(coordinatesList.isEmpty())
        {
            throw new IllegalStateException("an unknown state exeception occured; " + stateName);
        }
            
        return coordinatesList.get(0);
    }

    public List<String> stateNames()
    {
        return stateCoordinates.stream()
                .map(state -> state.getName())
                .collect( Collectors.toList() );
    }
}
