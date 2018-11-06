
package org.onebeartoe.filesystem.duplicates;

import java.io.IOException;
import org.testng.annotations.Test;

/**
 *
 * @author Roberto Marquez
 */
public class FindDuplicateFilenamesSpecification
{
    FindDuplicateFilenames implementation;
    
    public FindDuplicateFilenamesSpecification()
    {
        implementation = new FindDuplicateFilenames();
    }
    
    @Test(groups = {"unit"}, expectedExceptions = IOException.class)
    public void serviceRequest_faile_IOExeption() throws IOException
    {
        String [] args = {"target/"};
        
        implementation.serviceRequest(args);
    }
}
