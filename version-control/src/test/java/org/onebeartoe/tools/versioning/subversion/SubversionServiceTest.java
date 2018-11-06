
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
    
    public SubversionServiceTest()
    {
        implementation = new SubversionService();
    }
    
    @Test(groups = {"unit"})
    public void creationDate_pass_knownDate() throws Exception
    {
//        final String repositoryPath = "http://svn.effbot.org/public/elementtree/";
        final String repositoryPath = "https://svn.riouxsvn.com/junit-target/";
        
        String creationDate = implementation.creationDate(repositoryPath);
//System.out.println("cd: " + creationDate);

        String expectedDate = "2018-09-14 15:40:22 +0000 (Fri, 14 Sep 2018)";
//        String expectedDate = "2018-09-14 10:40:22 -0500 (Fri, 14 Sep 2018)";
//        String expectedDate = "2006-03-01 09:52:04 -0600 (Wed, 01 Mar 2006)";
        
        assert expectedDate.equals(creationDate);
    }
    
    @Test
    public void revertModified() throws IOException
    {
        String infile = "src/test/resources/data/subversion-service/blank.text";
        
        implementation.revertModified(infile);
    }
}
