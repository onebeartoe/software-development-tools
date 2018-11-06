
package org.onebeartoe.tools.versioning.subversion;

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
    
    @Test(groups = {"unit"})
    public void appendCreationDate() throws Exception
    {
        implementation.appendCreationDate(blankInfile);
    }
    
    @Test(groups = {"unit"})
    public void creationDate_pass_knownDate() throws Exception
    {
//        final String repositoryPath = "http://svn.effbot.org/public/elementtree/";
        final String repositoryPath = "https://svn.riouxsvn.com/junit-target/";
        
        String creationDate = implementation.creationDate(repositoryPath);


        String expectedDate = "2018-09-14 15:40:22 +0000 (Fri, 14 Sep 2018)";
        
        assert expectedDate.equals(creationDate);
    }
    
    @Test(groups = {"unit"})
    public void revertModified() throws IOException
    {        
        implementation.revertModified(blankInfile);
    }
}
