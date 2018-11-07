/*
 */
package org.onebeartoe.filesystem.populator;

import java.io.File;
import org.testng.annotations.Test;

/**
 *
 * @author Roberto Marquez
 */
public class FilesystemPopulaterServiceSpecification
{
    private FilesystemPopulaterService implementation;
    
    public FilesystemPopulaterServiceSpecification()
    {
        implementation = new FilesystemPopulaterService();
    }
    
    @Test(groups = {"unit"})
    public void serviceRequest() throws Exception
    {
        FilesystemPopulatorRunProfile runProfile = new FilesystemPopulatorRunProfile();
        runProfile.populationFile = new File("target/");
        
        runProfile.showNonOpenscadDirs = true;
        runProfile.showOpenScadDirs = true;
        
        implementation.serviceRequest(runProfile);
    }
}
