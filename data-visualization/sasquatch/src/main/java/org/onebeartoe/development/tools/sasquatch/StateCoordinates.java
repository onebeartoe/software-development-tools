
package org.onebeartoe.development.tools.sasquatch;

import com.opencsv.bean.CsvBindByName;

/**
 *
 */
public class StateCoordinates
{
    @CsvBindByName
    String latitude;
    
    @CsvBindByName
    String longitude;
    
    @CsvBindByName
    String name;
}
