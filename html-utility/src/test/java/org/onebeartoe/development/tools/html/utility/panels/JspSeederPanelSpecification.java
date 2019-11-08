
package org.onebeartoe.development.tools.html.utility.panels;

import java.awt.AWTException;
import org.apache.commons.lang3.StringUtils;
import org.onebeartoe.application.JavaPreferencesService;
import org.onebeartoe.system.Filesystem;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class JspSeederPanelSpecification
{
    JspSeederPanel implementation;
    
    @BeforeTest
    public void setup()
    {
        JavaPreferencesService preferencesService = new JavaPreferencesService( getClass() );
        
        implementation = new JspSeederPanel(preferencesService);
    }
    
    @Test
    public void textField_absolutePath() throws AWTException
    {
        String pwd = Filesystem.pwd();
        
        pwd = StringUtils.chop(pwd);
        
        String webappPath = "src/test/resources/jsp-seeder/webapp/";
        
        String subpath = "music/rock";
        
        String absolutePath = pwd 
//                + "/" 
                + webappPath + subpath;
        
        implementation.targetDirectory.setText(absolutePath);
        
        implementation.targetDirKeyListener.keyReleased(null);
        
        String targetPath = implementation.targetDirectory.getText();
       
        String actual = targetPath;
        
        String expected = subpath;
        
        assertEquals(actual, expected);
    }
    
    @Test
    public void textField_noAbsolutePath()
    {
        String relativePath = "some/path/that/is/not/absolute";
        
        implementation.targetDirectory.setText(relativePath);
        
        implementation.targetDirKeyListener.keyReleased(null);
        
        String targetPath = implementation.targetDirectory.getText();
       
        String actual = targetPath;
        
        String expected = relativePath;
        
        assertEquals(actual, expected);        
    }
}
