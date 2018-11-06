
package org.onebeartoe.filesystem.populator;

import java.io.IOException;
import org.apache.commons.cli.Options;
import org.onebeartoe.application.AppletService;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class FilesystemPopulaterSpecification
{
    FilesystemPopulater implementation;
    
    public FilesystemPopulaterSpecification()
    {
        implementation = new FilesystemPopulater();
    }
    
    @Test(groups = {"unit"})
    public void buildOptions()
    {
        Options buildOptions = implementation.buildOptions();
        
        assert(buildOptions != null);
    }
    
    @Test(groups = {"unit"})
    public void getService() throws IOException
    {
        AppletService service = implementation.getService();
        
        assert(service != null);
    }
}