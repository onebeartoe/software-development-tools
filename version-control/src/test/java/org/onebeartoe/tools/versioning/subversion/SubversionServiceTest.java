
package org.onebeartoe.tools.versioning.subversion;

import org.onebeartoe.tools.versioning.subversion.service.SubversionService;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class SubversionServiceTest
{
    public SubversionService subversionService;
    
    public SubversionServiceTest()
    {
        subversionService = new SubversionService();
    }
    
    @Test
    public void testKnownDate() throws Exception
    {
//        final String repositoryPath = "http://svn.effbot.org/public/elementtree/";
        final String repositoryPath = "https://svn.riouxsvn.com/junit-target/";
        
        String creationDate = subversionService.creationDate(repositoryPath);
//System.out.println("cd: " + creationDate);

        String expectedDate = "2018-09-14 10:40:22 -0500 (Fri, 14 Sep 2018)";
//        String expectedDate = "2006-03-01 09:52:04 -0600 (Wed, 01 Mar 2006)";
        
        assert expectedDate.equals(creationDate);
    }
}
