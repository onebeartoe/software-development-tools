/*
 */
package org.onebeartoe.file.types;

import org.testng.annotations.Test;

/**
 *
 * @author Roberto Marquez
 */
public class FileTypesServiceSpecification
{
    FileTypesService implementation;

    public FileTypesServiceSpecification()
    {
        implementation = new FileTypesService();
    }
    
    @Test(groups = {"unit"})
    public void serviceRequest() throws Exception
    {
        FileTypesRunProfile runProfile = new FileTypesRunProfile();
        runProfile.setInpath("target/");
        
        implementation.serviceRequest(runProfile);
    }    
}
