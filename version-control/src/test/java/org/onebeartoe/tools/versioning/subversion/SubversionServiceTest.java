
package org.onebeartoe.tools.versioning.subversion;

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
        final String repositoryPath = "http://svn.effbot.org/public/elementtree/";
        
        String creationDate = subversionService.creationDate(repositoryPath);
        
        String expectedDate = "2006-03-01 09:52:04 -0600 (Wed, 01 Mar 2006)";
        
        assert expectedDate.equals(creationDate);
    }
}
