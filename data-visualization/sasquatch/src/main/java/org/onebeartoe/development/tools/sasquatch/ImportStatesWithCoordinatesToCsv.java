
package org.onebeartoe.development.tools.sasquatch;

import java.io.InputStream;

/**
 *
 */
public class ImportStatesWithCoordinatesToCsv
{

//TODO: is this class still needed?        
    
    public static void main(String[] args)
    {
        ImportStatesWithCoordinatesToCsv app = new ImportStatesWithCoordinatesToCsv();
        
        app.importStates();
    }
    
    private void importStates()
    {
        String name = "states-236-14.tsv";
        
        InputStream inStream = ClassLoader.getSystemResourceAsStream(name);
        
        
    }
}
