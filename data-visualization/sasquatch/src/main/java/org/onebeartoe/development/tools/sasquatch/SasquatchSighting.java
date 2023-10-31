
package org.onebeartoe.development.tools.sasquatch;

import com.opencsv.bean.CsvBindByName;

/**
 *
 */
public class SasquatchSighting 
{
    @CsvBindByName
    public String date;
    
    @CsvBindByName
    public String state;
  
    public Long count;
    
    public String latitude;

    public String longitude;
    
    public String getState()
    {
        return state;
    }
}
