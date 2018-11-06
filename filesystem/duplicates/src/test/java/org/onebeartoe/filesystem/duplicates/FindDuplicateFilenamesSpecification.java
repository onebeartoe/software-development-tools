
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
    
    @Test(groups = {"unit"})
    public void serviceRequest() throws IOException
    {
        String inpath = "src/test/resources/data/find-duplicate-filenames/04-duplicates.text";
        
        String [] args = {inpath};
        
        implementation.serviceRequest(args);
    }
    
    @Test(groups = {"unit"}, expectedExceptions = IOException.class)
    public void serviceRequest_fail_IOExeption() throws IOException
    {
        String [] args = {"target/does-not-exist.text"};
        
        implementation.serviceRequest(args);
    }
}
