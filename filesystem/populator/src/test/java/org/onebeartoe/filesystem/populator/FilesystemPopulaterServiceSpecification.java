
package org.onebeartoe.filesystem.populator;

import java.io.File;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class FilesystemPopulaterServiceSpecification
{
    private FilesystemPopulaterService implementation;
    
    private final File targetDirectory = new File("target/");
    
    public FilesystemPopulaterServiceSpecification()
    {
        implementation = new FilesystemPopulaterService();
    }
    
    @Test(groups = {"unit"})
    public void serviceRequest_showOnly() throws Exception
    {
        FilesystemPopulatorRunProfile runProfile = new FilesystemPopulatorRunProfile();
        runProfile.populationFile = targetDirectory;
        
        runProfile.showNonOpenscadDirs = true;
        runProfile.showOpenScadDirs = true;
        
        implementation.serviceRequest(runProfile);
    }
    
    @Test(groups = {"unit"})
    public void serviceRequest_populate() throws Exception
    {
        FilesystemPopulatorRunProfile runProfile = serviceRequest_populate_getRunProfile();

        implementation.serviceRequest(runProfile);
    }
    
    private FilesystemPopulatorRunProfile serviceRequest_populate_getRunProfile()
    {
        FilesystemPopulatorRunProfile runProfile = new FilesystemPopulatorRunProfile();
        
        runProfile.populationFile = targetDirectory;
        
        runProfile.showNonOpenscadDirs = true;
        runProfile.showOpenScadDirs = false;
        
        // setup a population diretory
        String outpath = "target/data/FilesystemPopulatorRunProfile/";
        File outputDir = new File(outpath);
        outputDir.mkdirs();
        
        runProfile.openScadDirectories.add(outpath);
        
        return runProfile;
    }
    
    @Test(groups = {"unit"})
    public void serviceRequest_populate_noNonScads() throws Exception
    {
        FilesystemPopulatorRunProfile runProfile = serviceRequest_populate_getRunProfile();
        runProfile.showNonOpenscadDirs = false;
        
        implementation.serviceRequest(runProfile);        
    }
}
