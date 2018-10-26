
package org.onebeartoe.filesystem.duplicates;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
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
    
    @Test(groups = {"unit"})
    public void preVisitdirectory() throws IOException
    {
        String dirPath = "target/classes";
        File f = new File(dirPath);
        Path dir = f.toPath();
        
        BasicFileAttributes attributes = null;
        
        implementation.preVisitDirectory(dir, attributes);
    }
}
