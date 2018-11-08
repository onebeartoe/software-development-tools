
package org.onebeartoe.tools.versioning.subversion;

import java.io.File;
import java.io.IOException;
import org.onebeartoe.tools.versioning.subversion.service.SubversionService;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class SubversionServiceTest
{
    public SubversionService implementation;
    
    public final String blankInfile = "src/test/resources/data/subversion-service/blank.text";
    
    public SubversionServiceTest()
    {
        implementation = new SubversionService();
    }
    
    @Test(groups = {"unit"}, expectedExceptions = IllegalArgumentException.class)
    public void appendCreationDate_fail_blankInfile() throws Exception
    {
        implementation.appendCreationDate(blankInfile);
    }
    
    @Test(groups = {"unit"})
    public void creationDate_pass_knownDate() throws Exception
    {
        final String repositoryPath = "https://svn.riouxsvn.com/junit-target/";
        
        String actualDate = implementation.creationDate(repositoryPath);


        // local workspace
//        String expectedDate = "2018-09-14 10:40:22 -0500 (Fri, 14 Sep 2018)";
  
        // travis-ci workspace
        String expectedDate = "2018-09-14 15:40:22 +0000 (Fri, 14 Sep 2018)";

        assert expectedDate.equals(actualDate);
    }
    
    @Test(groups = {"unit"})
    public void revertModified_oneLine() throws IOException
    {        
        String sssinpath = "src/test/resources/data/subversion-service/one-line.text";
        
        implementation.revertModified(sssinpath);
    }
}
