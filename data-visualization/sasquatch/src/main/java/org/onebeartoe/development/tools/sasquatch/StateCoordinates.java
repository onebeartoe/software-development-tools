
package org.onebeartoe.development.tools.sasquatch;

import com.opencsv.bean.CsvBindByName;

/**
 *
 */
public class StateCoordinates
{
    @CsvBindByName
    public String latitude;
    
    @CsvBindByName
    public String longitude;
    
    @CsvBindByName
    public String name;

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
}
