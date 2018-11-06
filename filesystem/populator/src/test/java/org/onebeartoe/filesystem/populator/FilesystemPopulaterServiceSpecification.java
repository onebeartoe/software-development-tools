/*
 */
package org.onebeartoe.filesystem.populator;

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
        
        implementation.serviceRequest(runProfile);
    }
}
