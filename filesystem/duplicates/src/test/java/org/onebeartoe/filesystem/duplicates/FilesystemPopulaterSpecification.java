
package org.onebeartoe.filesystem.duplicates;

import org.apache.commons.cli.Options;
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
}
