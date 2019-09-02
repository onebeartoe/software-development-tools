
package org.onebeartoe.development.tools.html.utility.tasks;

import java.io.File;
import org.apache.commons.lang3.StringUtils;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Roberto Marquez
 */
public class ImageTaskServiceSpecification
{
    ImageTaskService implementation;
    

    @BeforeMethod
    public void setUpMethod() throws Exception
    {
        implementation = new ImageTaskService();
    }

    /**
     * Test of index method, of class ImageTaskService.
     */
    @Test
    public void testIndex()
    {
        String imagePath = "src/test/resources/image-task/";
        
        File imageDir = new File(imagePath);
            
        boolean includeWebInfPath = true;
        
        String html = implementation.index(imageDir, includeWebInfPath);
        
        String target = "<img src=";
        
        int expected = 2;
        
        int actual = StringUtils.countMatches(html, target);
        
        assertEquals(actual, expected);
    }
    
}
