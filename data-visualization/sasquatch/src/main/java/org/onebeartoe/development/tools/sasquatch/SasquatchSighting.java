
package org.onebeartoe.development.tools.sasquatch;

import com.opencsv.bean.CsvBindByName;

/**
 *
 */
public class SasquatchSighting 
{
    @CsvBindByName
    public String state;
    
    public String stateAbbreviation;
    
//    @CsvBindByName
    public float latitude;
    
//    @CsvBindByName
    public float longitude;
    
    public String getState()
    {
        return state;
    }
}
